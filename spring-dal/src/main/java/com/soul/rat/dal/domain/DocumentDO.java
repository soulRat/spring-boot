package com.soul.rat.dal.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 文档记录
 * </p>
 *
 * @author 朱家兴
 * @since 2022-01-05
 */
@Data
@TableName("soul_document")
@ApiModel(value = "DocumentDO对象", description = "")
public class DocumentDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("创建时间")
    private Date gmtCreated;

    @ApiModelProperty("修改时间")
    private Date gmtModified;

    @ApiModelProperty("是否删除：0-未删除 1-已删除")
    private Integer isDeleted;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("分类")
    private String category;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("oss路径")
    private String images;


}
