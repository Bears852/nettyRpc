package com.recklessMo.rpc.annotation;

import java.lang.annotation.*;

/**
 *
 * 用于标记一个service
 *
 * Created by hpf on 11/30/17.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface RpcServiceFlag {
    String value();
}
