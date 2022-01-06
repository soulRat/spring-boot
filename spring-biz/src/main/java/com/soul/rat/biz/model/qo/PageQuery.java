package com.soul.rat.biz.model.qo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页查询
 *
 * @author zhujx
 * @date 2021/12/06 17:39
 */
@Data
public class PageQuery {


    @ApiModelProperty("当前页")
    private Long pageNum;

    @ApiModelProperty("每页条数")
    private Long pageSize;

}
