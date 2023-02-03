package com.kismallmi.myutils.aop;

import java.lang.annotation.*;

/**
 * @version 1.0
 * @Author wangjian
 * @since 2023/2/3
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation{
    String value() default "";
//    Class responseType();
//    Class requestType();


}
