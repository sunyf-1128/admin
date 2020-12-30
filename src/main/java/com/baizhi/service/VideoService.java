package com.baizhi.service;

import com.baizhi.entity.Video;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface VideoService {

    //获取所有视频
    public List<Video> videoPage(Integer page, Integer rows);

    //添加视频
    public HashMap<String, String> addVideo(Video video, MultipartFile fileVideo) throws IOException;

    //删除视频
    public String delVideo(Video video);

    //根据id修改视频信息
    public HashMap<String, String> updateVideo(MultipartFile fileVideo, Video video) throws IOException;

    //根据id查视频信息
    public Video queryOneVideo(Video video);
}
