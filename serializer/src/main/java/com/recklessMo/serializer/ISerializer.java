package com.recklessMo.serializer;

/**
 * Created by hpf on 11/18/17.
 */
public interface ISerializer {

    byte[] serialize(Object a);
    Object deserialize(byte[] data);

}
