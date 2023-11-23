package com.xcy.gxyg.common.exception;


import com.za.common.res.Res;
import com.za.common.res.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Ming
 */
@Slf4j
public class Assert {

    /**
     * 断言文件不为空
     * obj 为空则抛异常
     *
     * @param file
     * @param responseEnum
     */
    public static void fileNotNull(MultipartFile file, ResponseEnum responseEnum) {
        if (file.isEmpty()) {

            log.info("FILE is null.....................");
            throw new BusinessException(responseEnum);
        }
    }

    /**
     * 断言集合不为空
     * 如果对象obj不为空，则抛出异常
     *
     * @param list
     * @param responseEnum
     */
    public static void listNotNull(List<?> list, ResponseEnum responseEnum) {
        if (list == null || list.isEmpty()) {
            log.info("list is  null......");
            throw new BusinessException(responseEnum);
        }
    }

    public static void listNull(List<?> list, ResponseEnum responseEnum) {
        if (list != null && !list.isEmpty()) {
            log.info("list is not null......");
            throw new BusinessException(responseEnum);
        }
    }


    /**
     * 断言对象不为空
     * obj 为空则抛异常
     *
     * @param obj
     * @param responseEnum
     */
    public static void notNull(Object obj, ResponseEnum responseEnum) {
        if (obj == null) {
            log.info("obj is null.....................");
            throw new BusinessException(responseEnum);
        }
    }

    /**
     * 断言对象不为空
     * obj1 和obj2 都为空则抛异常
     *
     * @param l1
     * @param l2
     * @param responseEnum
     */
    public static void allNotNullAndNotO(Long l1, Long l2, ResponseEnum responseEnum) {
        if (l1 == null && l2 == null) {
            log.info("obj1 and obj2 is null.....................");
            throw new BusinessException(responseEnum);
        }
        if (l1 == 0 && l2 == 0) {
            log.info("obj1 and obj2 is 0.....................");
            throw new BusinessException(responseEnum);
        }
    }


    /**
     * 断言对象为空
     * 如果对象obj不为空，则抛出异常
     *
     * @param object
     * @param responseEnum
     */
    public static void isNull(Object object, ResponseEnum responseEnum) {
        if (object != null) {
            log.info("obj is not null......");
            throw new BusinessException(responseEnum);
        }
    }

    /**
     * 断言返回结果为成功为真
     * 如果不为真，则抛出异常
     */
    public static void isTrue(Res r, ResponseEnum responseEnum) {
        if (!r.getCode().equals(ResponseEnum.SUCCESS.getCode())) {
            log.info("fail...............");
            throw new BusinessException(responseEnum);
        }
    }

    /**
     * 断言表达式为真
     * 如果不为真，则抛出异常
     *
     * @param expression 是否成功
     */
    public static void isTrue(boolean expression, ResponseEnum responseEnum) {
        if (!expression) {
            log.info("fail...............");
            throw new BusinessException(responseEnum);
        }
    }

    /**
     * 断言两个对象不相等
     * 如果相等，则抛出异常
     *
     * @param m1
     * @param m2
     * @param responseEnum
     */
    public static void notEquals(Object m1, Object m2, ResponseEnum responseEnum) {
        if (m1.equals(m2)) {
            log.info("equals...............");
            throw new BusinessException(responseEnum);
        }
    }

    /**
     * 断言两个对象相等
     * 如果不相等，则抛出异常
     *
     * @param m1
     * @param m2
     * @param responseEnum
     */
    public static void equals(Object m1, Object m2, ResponseEnum responseEnum) {
        if (!m1.equals(m2)) {
            log.info("not equals...............");
            throw new BusinessException(responseEnum);
        }
    }

    /**
     * 断言参数不为空
     * 如果为空，则抛出异常
     *
     * @param s
     * @param responseEnum
     */
    public static void notEmpty(String s, ResponseEnum responseEnum) {
        if (StringUtils.isEmpty(s)) {
            log.info("is empty...............");
            throw new BusinessException(responseEnum);
        }
    }


}