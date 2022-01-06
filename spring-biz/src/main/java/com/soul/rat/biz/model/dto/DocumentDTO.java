package com.soul.rat.biz.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 项目
 *
 * @author zhujx
 * @date 2021/12/27 18:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "document", replicas = 0)
public class DocumentDTO {


    @Id
    private Long id;

    /**
     * 标题
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;

    /**
     * 分类
     */
    @Field(type = FieldType.Keyword)
    private String category;

    /**
     * 内容
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String content;

    /**
     * 地址
     */
    @Field(index = false, type = FieldType.Keyword)
    private String images;


}
