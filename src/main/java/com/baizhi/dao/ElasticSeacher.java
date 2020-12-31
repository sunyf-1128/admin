package com.baizhi.dao;

import com.baizhi.entity.Video;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface ElasticSeacher extends ElasticsearchRepository<Video, String> {
}
