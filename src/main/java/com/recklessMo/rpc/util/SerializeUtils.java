package com.recklessMo.rpc.util;

import com.recklessMo.rpc.model.RequestWrapper;
import io.netty.buffer.ByteBuf;

import java.io.*;
import java.util.List;

/**
 * Created by hpf on 11/22/17.
 */
public class SerializeUtils {


    /**
     *
     * 写object到buffer中
     *
     * @param out
     * @param obj
     */
    public static <T extends Serializable> void writeObject(ByteBuf out, T obj) throws Exception{
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(array);
        objectOutputStream.writeObject(obj);
        byte[] data = array.toByteArray();
        int length = data.length;
        out.writeInt(length);
        out.writeBytes(data);
    }

    /**
     *
     * 从流中获取object
     *
     * @param in
     * @param out
     */
    public static void getObject(ByteBuf in, List<Object> out) throws Exception{
        if(in.readableBytes() < 4){
            return;
        }
        in.markReaderIndex();
        int length = in.readInt();
        if(in.readableBytes() < length){
            in.resetReaderIndex();
            return;
        }
        byte[] data = new byte[length];
        in.readBytes(data);
        ByteArrayInputStream array = new ByteArrayInputStream(data);
        ObjectInputStream inputStream = new ObjectInputStream(array);
        Object result = inputStream.readObject();
        out.add(result);
    }


    public static void main(String[] args) throws Exception{

        RequestWrapper data = new RequestWrapper();
        data.setServiceName("xxx");
        data.setRequestId("yyy");
//        SerializeUtils.writeObject();
        SerializeUtils.writeObject(null, data);



    }

}
