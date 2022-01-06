package com.soul.rat.biz.repository;

import com.soul.rat.biz.model.dto.DocumentDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * 文档记录存储器
 *
 * @author zhujx
 */
@Component
public interface DocumentRepository extends ElasticsearchRepository<DocumentDTO, Long> {

}
