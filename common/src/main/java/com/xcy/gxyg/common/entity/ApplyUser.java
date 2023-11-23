package com.xcy.gxyg.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author zhiMing
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "客户端用户", description = "客户端用户")
public class ApplyUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "编号")
    private Long account;

    @ApiModelProperty(value = "阿里用户ID")
    private String aliAccount;

    @ApiModelProperty(value = "手机")
    private String sim;

    @ApiModelProperty(value = "手机MD5")
    private String simMd5;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "身份证号")
    private String identityCard;

    @ApiModelProperty(value = "身份证号Md5")
    private String identityCardMd5;

    @ApiModelProperty(value = "性别1 男;2 女")
    private Integer sex;

    @ApiModelProperty(value = "城市  福建省-福州市")
    private String city;

    @ApiModelProperty(value = "年龄")
    private Integer age;
}
