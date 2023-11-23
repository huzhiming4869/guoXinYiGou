package com.xcy.gxyg.common.res;


import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ming
 */
@Data
public class Res {

    private Integer code;

    private Integer show = -1;

    private String message;

    private Map<String, Object> data = new HashMap<>();

    /**
     * 构造器私有
     */
    private Res() {
    }

    /**
     * 返回成功
     */
    public static Res ok() {
        Res r = new Res();
        r.setCode(ResponseEnum.SUCCESS.getCode());
        r.setMessage(ResponseEnum.SUCCESS.getMessage());
        return r;
    }


    /**
     * 返回成功
     */
    public static Res showOkMsg() {
        Res r = new Res();
        r.setShow(0);
        r.setCode(ResponseEnum.SUCCESS.getCode());
        r.setMessage(ResponseEnum.SUCCESS.getMessage());
        return r;
    }

    /**
     * 返回失败
     */
    public static Res error() {
        Res r = new Res();
        r.setCode(ResponseEnum.ERROR.getCode());
        r.setMessage(ResponseEnum.ERROR.getMessage());
        return r;
    }

    public static Res error(ResponseEnum responseEnum) {
        Res r = new Res();
        r.setCode(responseEnum.getCode());
        r.setMessage(responseEnum.getMessage());
        return r;
    }

    /**
     * 设置特定结果
     */
    public static Res setResult(ResponseEnum responseEnum) {
        Res r = new Res();
        r.setCode(responseEnum.getCode());
        r.setMessage(responseEnum.getMessage());
        return r;
    }

    public Res message(String message) {
        this.setMessage(message);
        return this;
    }

    public Res code(Integer code) {
        this.setCode(code);
        return this;
    }

    public Res data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public Res data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }

}