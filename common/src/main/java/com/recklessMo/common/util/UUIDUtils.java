package com.recklessMo.common.util;

import java.util.UUID;

/**
 * Created by hpf on 11/20/17.
 */
public class UUIDUtils {

    //todo 暂时不处理
    public static String getRandomId(){
        //return String.valueOf(System.currentTimeMillis());
        return UUID.randomUUID().toString();
    }

}
