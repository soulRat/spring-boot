package com.soul.rat.dal.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author 朱家兴
 * @since 2021-12-17
 */
@Data
@TableName("tb_count")
@ApiModel(value = "CountDO对象", description = "")
public class CountDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("创建时间")
    @TableField("gmt_created")
    private Date gmtCreated;

    @ApiModelProperty("修改时间")
    @TableField("gmt_modified")
    private Date gmtModified;

    @ApiModelProperty("是否删除：0-未删除 1-已删除")
    @TableField("is_deleted")
    private Integer isDeleted;

    @TableField("num")
    private Integer num;


}
