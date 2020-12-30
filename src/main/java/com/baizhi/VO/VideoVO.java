package com.baizhi.VO;

import java.util.Date;

public class VideoVO {
    String id;//": "55af1db8af21410691d154f61b245be5",
    String videoTitle;//":"动画",
    String cover;//":"http://q40vnlbog.bkt.clouddn.com/1578650435020_动画.jpg",
    String path;//":"http://q3th1ypw9.bkt.clouddn.com/1578650435020_动画.mp4",
    Date uploadTime;//":"2020-01-08",
    String description;//":"动画",
    Integer likeCount;//":2,
    String cateName;//":"Java"

    @Override
    public String toString() {
        return "Video{" +
                "id='" + id + '\'' +
                ", videoTitle='" + videoTitle + '\'' +
                ", cover='" + cover + '\'' +
                ", path='" + path + '\'' +
                ", uploadTime=" + uploadTime +
                ", description='" + description + '\'' +
                ", likeCount=" + likeCount +
                ", cateName='" + cateName + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }
}
