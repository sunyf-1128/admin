package com.baizhi.service;

import com.baizhi.aliyun.aliyunUploadDelFragment;
import com.baizhi.aspect.MyLog;
import com.baizhi.dao.CategoryMapper;
import com.baizhi.dao.VideoMapper;
import com.baizhi.entity.Admin;
import com.baizhi.entity.Category;
import com.baizhi.entity.Video;
import com.baizhi.entity.VideoExample;
import com.baizhi.util.PageBegin;
import com.baizhi.util.id;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class VideoServiceImpl implements VideoService {

    @Resource
    private HttpSession session;

    @Resource
    private VideoMapper videoMapper;

    @Resource
    private CategoryMapper categoryMapper;


    //查所有视频并分页
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Video> videoPage(Integer page, Integer rows) {

        //获取开始条数
        Integer begin = PageBegin.getBegin(page, rows);

        VideoExample example = new VideoExample();

        RowBounds bounds = new RowBounds(begin - 1, rows);

        List<Video> videos = videoMapper.selectByExampleAndRowBounds(example, bounds);

        for (Video video : videos) {
            if (video.getCategoryId() == null || video.getCategoryId().equals("null")) {
                //System.out.println(123);
                video.setCategoryId("8");
            }
            //System.out.println(video.getCategoryId());
            //获取二级类别
            Category category2 = categoryMapper.selectByPrimaryKey(video.getCategoryId());
            //  System.out.println("sdf:");
            //  System.out.println(category2);
            //System.out.println(category2.getParentId());
            //获取一级类别
            Category category = categoryMapper.selectByPrimaryKey(category2.getParentId());
            // System.out.println(category);
            //将一级类别名set进video中
            video.setCateName(category.getName());
        }
        return videos;
    }

    //添加视频
    @MyLog("添加视频")
    @Override
    public HashMap<String, String> addVideo(Video video, MultipartFile fileVideo) throws IOException {
        HashMap<String, String> map = new HashMap<String, String>();
        video.setId(id.getId());
        Admin admin = (Admin) session.getAttribute("adminLogin");


        if (video.getCategoryId() == null) {
            map.put("status", "400");
            map.put("message", "请选择所属的类别分类");
        } else if (admin != null) {
            video.setUserId(admin.getId());
            video.setCreateDate(new Date());
            video.setGroupId("1");//暂时没有用户的视频分组，先模拟
            video.setStatus("normal");
            //  System.out.println(fileVideo.getBytes().length != 0);
            if (fileVideo.getBytes().length != 0) {
                //获取视频的文件名
                String fileName = new Date().getTime() + "-" + fileVideo.getOriginalFilename();


                video.setAddress("https://sunyf-yx.oss-cn-beijing.aliyuncs.com/video/" + fileName);
                int i = fileName.indexOf(".");
                String cover = fileName.substring(0, i) + ".jpg";
                video.setCover("https://sunyf-yx.oss-cn-beijing.aliyuncs.com/video-cover/" + cover);
                //System.out.println("封面：" + cover);
                //调用aliAPI上传视频
                aliyunUploadDelFragment.upload(fileName, "sunyf-yx", "video/", fileVideo);
                //调用aliAPI截取视频1.01s时的帧
                URL url = aliyunUploadDelFragment.fragment("sunyf-yx", "video/", fileName);

                // System.out.println(url.openStream());
                //调用aliAPI上传视频封面
                aliyunUploadDelFragment.internetUpload("sunyf-yx", "video-cover/", cover, url);
                //将视频信息保存至数据库
                videoMapper.insertSelective(video);
                map.put("status", "200");
                map.put("message", "您已成功添加视频");
            } else {
                map.put("status", "400");
                map.put("message", "请选择您要添加的视频！");
            }
        } else {
            map.put("status", "400");
            map.put("message", "您还没有登录请您登录账号再进行操作！5s后将跳转至登录界面！");
        }
        return map;
    }

    //删除视频  只需删除数据库信息  aliyun对应视频即可
    @MyLog("删除视频")
    @Override
    public String delVideo(Video video) {

        Video video1 = videoMapper.selectByPrimaryKey(video.getId());
        //获得/video/的下一个下标
        int videoBegin = video1.getAddress().indexOf("/video/") + 7;
        //截取视频名
        String oldVideoFileName = video1.getAddress().substring(videoBegin, video1.getAddress().length());
        int coverBegin = video1.getCover().indexOf("/video-cover/") + 13;
        //截取封面名称
        String oldCoverName = video1.getCover().substring(coverBegin, video1.getCover().length());
        //调用aliyun的API删除原有的视频及封面
        aliyunUploadDelFragment.del(oldVideoFileName, "sunyf-yx", "video/");
        aliyunUploadDelFragment.del(oldCoverName, "sunyf-yx", "video-cover/");
        videoMapper.deleteByPrimaryKey(video);
        return "您已成功删除该视频";
    }

    //根据id修改视频信息
    @MyLog("修改视频信息")
    @Override
    public HashMap<String, String> updateVideo(MultipartFile fileVideo, Video video) throws IOException {
        HashMap<String, String> map = new HashMap<String, String>();

        if (video.getTitle() == null) {
            map.put("status", "400");
            map.put("message", "视频标题不能为空，请填写视频标题");
        } else if (fileVideo.getBytes().length != 0) {
            Video video1 = videoMapper.selectByPrimaryKey(video.getId());
            //获得/video/的下一个下标
            int videoBegin = video1.getAddress().indexOf("/video/") + 7;
            //截取视频名
            String oldVideoFileName = video1.getAddress().substring(videoBegin, video1.getAddress().length());
            int coverBegin = video1.getCover().indexOf("/video-cover/") + 13;
            //截取封面名称
            String oldCoverName = video1.getCover().substring(coverBegin, video1.getCover().length());
            //调用aliyun的API删除原有的视频及封面
            aliyunUploadDelFragment.del(oldVideoFileName, "sunyf-yx", "video/");
            aliyunUploadDelFragment.del(oldCoverName, "sunyf-yx", "video-cover/");

            //以下为上传新视频及封面


            //获取视频的文件名
            String fileName = new Date().getTime() + "-" + fileVideo.getOriginalFilename();

            video.setAddress("https://sunyf-yx.oss-cn-beijing.aliyuncs.com/video/" + fileName);
            int i = fileName.indexOf(".");
            String cover = fileName.substring(0, i) + ".jpg";
            video.setCover("https://sunyf-yx.oss-cn-beijing.aliyuncs.com/video-cover/" + cover);
            //System.out.println("封面：" + cover);
            //调用aliAPI上传视频
            aliyunUploadDelFragment.upload(fileName, "sunyf-yx", "video/", fileVideo);
            //调用aliAPI截取视频1.01s时的帧
            URL url = aliyunUploadDelFragment.fragment("sunyf-yx", "video/", fileName);

            // System.out.println(url.openStream());
            //调用aliAPI上传视频封面
            aliyunUploadDelFragment.internetUpload("sunyf-yx", "video-cover/", cover, url);
            //将视频信息保存至数据库
            if (video.getCategoryId().equals("null")) {
                video.setCategoryId(null);
            }
            if (video.getIntro().equals("null")) {
                video.setIntro(null);
            }
            videoMapper.updateByPrimaryKeySelective(video);
            map.put("status", "200");
            map.put("message", "您已成功修改视频信息");
        } else {
            if (video.getCategoryId().equals("null")) {
                video.setCategoryId(null);
            }
            if (video.getIntro().equals("null")) {
                video.setIntro(null);
            }
            videoMapper.updateByPrimaryKeySelective(video);
            map.put("status", "200");
            map.put("message", "您已成功修改视频信息");
        }
        return map;
    }

    //根据id查视频信息
    @Override
    public Video queryOneVideo(Video video) {
        return videoMapper.selectByPrimaryKey(video);
    }
}
