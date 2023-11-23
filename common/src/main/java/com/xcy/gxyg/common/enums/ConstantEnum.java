package com.xcy.gxyg.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Ming
 */
@Getter
@AllArgsConstructor
@ToString
public enum ConstantEnum {

    /**
     * 用户身份相关
     */
    USER_ROLE_ROOT(1, "ROOT"),
    USER_ROLE_ADMIN(2, "管理员"),
    USER_ROLE_COMM(3, "总监"),
    USER_ROLE_GROUP(4, "组长"),
    USER_ROLE_SALESMAN(5, "业务员"),
    USER_ROLE_CHANNEL(6, "渠道"),


    /**
     * 开关状态
     * 封禁状态 等
     * 打开 0关  1开
     */
    CLOSE(0, "关闭"),
    OPEN(1, "打开"),


    /**
     * 用户状态
     */
    USER_STATUS_NORMAL(0, "正常"),
    USER_STATUS_BAN(1, "封禁"),


    /**
     * 黑名单  "黑名单类型  1=ip   2=phone   3=idcard
     */
    BLACK_TYPE_IP(1, "IP黑名单"),
    BLACK_TYPE_SIM(2, "手机黑名单"),
    BLACK_TYPE_ID_CARD(3, "身份证黑名单"),

    /**
     * 黑名单 命中 0正常，1黑名单
     */

    BLACK_NO(0, "正常"),
    BLACK_YES(1, "黑名单"),


    ;

    /**
     * 响应状态码
     */
    private Integer code;
    /**
     * 响应信息
     */
    private String message;
}
