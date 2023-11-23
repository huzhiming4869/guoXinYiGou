package com.xcy.gxyg.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Ming
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "后台用户", description = "后台用户")
public class BackstageUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "编号")
    private Long account;

    @ApiModelProperty(value = "权限ID 1：ROOT 2:管理员; 3:总监;4:组长; 5:业务员, 6:渠道;")
    private Long roleId;

    @ApiModelProperty(value = "分配权重id")
    private Long weight;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "登录名")
    private String loginName;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "是否开启")
    private Integer isOpen;

    @ApiModelProperty(value = "状态 0：正常")
    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "逻辑删除(1:已删除，0:未删除)")
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;


}