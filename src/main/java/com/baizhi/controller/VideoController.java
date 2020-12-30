package com.baizhi.controller;

import com.baizhi.entity.Video;
import com.baizhi.service.VideoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/video")
public class VideoController {

    @Resource
    private VideoService videoService;

    //获取所有视频并分页
    @RequestMapping("/videoPage")
    public List<Video> videoPage(Integer page, Integer rows) {
        return videoService.videoPage(page, rows);
    }

    /*
        //添加、修改、删除视频 用模态框替代了     此方法废弃  且此方法未完成
        public String edit(Video video, String oper, HttpSession session) {
            String message = null;
            if (oper.equals("add")) {
                Admin admin = (Admin) session.getAttribute("adminLogin");
                if (admin != null) {
                    //由于user暂时没写登录，没接入短信验证，故先用admin账户模拟user账户操作
                    video.setUserId(admin.getId());
                    video.setCreateDate(new Date());
                    //分组功能暂时没做，模拟分组功能
                    video.setGroupId("1");

                }

            } else if (oper.equals("edit")) {
                video.setAddress(null);
            } else {

            }
            return message;
        }
    */
    //添加视频
    @RequestMapping("/addVideo")
    public HashMap<String, String> addVideo(HttpServletResponse response, Video video, MultipartFile fileVideo) throws IOException {
        // System.out.println("addVideo：" + fileVideo);
        // System.out.println(video);
        return videoService.addVideo(video, fileVideo);
    }

    //根据id查视频信息
    @RequestMapping("/queryOneVideo")
    public Video queryOneVideo(Video video) {

        return videoService.queryOneVideo(video);
    }

    //修改视频
    @RequestMapping("/updateVideo")
    public HashMap<String, String> updateVideo(MultipartFile fileVideo1, Video video) throws IOException {
        //System.out.println(video.getCategoryId());
        return videoService.updateVideo(fileVideo1, video);
    }

    //删除视频
    @RequestMapping("/deleteVideo")
    public String deleteVideo(Video video) {
        return videoService.delVideo(video);
    }
}
