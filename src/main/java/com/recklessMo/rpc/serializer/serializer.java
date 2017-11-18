package com.recklessMo.rpc.serializer;

/**
 * Created by hpf on 11/18/17.
 */
public interface Serializer {

    byte[] serialize(Object a);
    Object deserialize(byte[] data);

}
