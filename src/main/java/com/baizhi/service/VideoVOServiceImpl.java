package com.baizhi.service;

import com.baizhi.VO.VideoVO;
import com.baizhi.dao.CategoryMapper;
import com.baizhi.dao.VideoMapper;
import com.baizhi.entity.Category;
import com.baizhi.entity.Video;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class VideoVOServiceImpl implements VideoVOService {
    @Resource
    private VideoMapper videoMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<VideoVO> queryAll() {
        List<Video> videos = videoMapper.selectAll();
        List<VideoVO> vos = new ArrayList<>();
        VideoVO videoVO = new VideoVO();
        for (Video video : videos) {
            Category category = categoryMapper.selectByPrimaryKey(video.getCategoryId());
            videoVO.setId(video.getId());
            videoVO.setUploadTime(video.getCreateDate());
            videoVO.setVideoTitle(video.getTitle());
            videoVO.setPath(video.getAddress());
            videoVO.setLikeCount(0);
            videoVO.setDescription(video.getIntro());
            videoVO.setCateName(category.getName());
            videoVO.setCover(video.getCover());

            vos.add(videoVO);
        }
        return vos;
    }
}
