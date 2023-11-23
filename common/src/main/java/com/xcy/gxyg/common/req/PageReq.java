package com.xcy.gxyg.common.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Ming
 */
@Data
@ApiModel(value = "分页参数", description = "分页参数")
public class PageReq {

    @ApiModelProperty(value = "页面大小")
    private Integer pageSize = 10;

    @ApiModelProperty(value = "页码")
    private Integer pageNum = 1;
}
