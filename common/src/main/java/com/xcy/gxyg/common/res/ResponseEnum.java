package com.xcy.gxyg.common.res;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Ming
 */
@Getter
@AllArgsConstructor
@ToString
public enum ResponseEnum {

    //初始返回
    SUCCESS(0, "成功"),
    ERROR(-1, "失败"),

    //-1xx 服务器错误
    BAD_SQL_GRAMMAR_ERROR(-101, "执行进程错误"),
    SERVLET_ERROR(-102, "请求异常"),

    //-2xx 参数校验
    BORROW_AMOUNT_NULL_ERROR(-201, "借款额度不能为空"),
    MOBILE_NULL_ERROR(-202, "手机号码不能为空"),
    MOBILE_ERROR(-203, "手机号码不正确"),
    LOGINCODE_NULL_ERROR(-204, "账号不能为空"),
    PASSWORD_NULL_ERROR(-205, "密码不能为空"),
    CODE_NULL_ERROR(-206, "验证码不能为空"),
    CODE_ERROR(-207, "验证码错误"),
    CODE_OVERDUE(-208, "验证码已过期"),
    MOBILE_EXIST_ERROR(-209, "手机号已被注册"),
    NO_SUCH_USER(-210, "用户不存在"),
    LOGIN_PASSWORD_ERROR(-211, "密码错误"),
    LOGIN_LOKED_ERROR(-212, "用户被锁定"),
    LOGIN_AUTH_ERROR(-213, "未登陆或登陆状态失效"),
    ID_CARD_NUM_ERROT(-214, "身份证号为空"),
    ID_CARD_NAME_ERROR(-215, "姓名为空"),
    PARAMETER_NULL_ERROR(-216, "参数为空"),
    LOGIN_ERROR(-218, "登陆失败"),


    // -3xx 执行结果返回
    ADD_FAIL(-301, "添加失败"),
    UPDATE_FAIL(-302, "修改失败"),
    DELETE_FAIL(-303, "删除失败"),
    QUERY_FAIL(-304, "查询失败"),
    EXPORT_FAIL(-305, "导出失败"),
    UPLOAD_FAIL(-306, "上传失败"),
    FILE_DOWNLOAD_FAIL(-307, "文件下载失败"),

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