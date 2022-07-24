package com.web;

import lombok.SneakyThrows;
import org.apache.shiro.session.Session;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

/**
 * @Author zhang guoxiang
 * @Date 2022/7/11
 */
public abstract class SerializableUtils {

    @SneakyThrows
    public static String serializ(Session session) {
        //ByteArrayOutputStream 用于存储序列化的Session对象
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        //将Object对象输出成byte数据
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(session);

        //将字节码，编码成String类型数据
        return Base64.getEncoder().encodeToString(bos.toByteArray());
    }

    @SneakyThrows
    public static Session deserializ(String sessionStr) {
        //读取字节码表
        ByteArrayInputStream bis = new ByteArrayInputStream(Base64.getDecoder().decode(sessionStr));

        //将字节码反序列化成 对象
        ObjectInputStream in = new ObjectInputStream(bis);
        Session session = (Session) in.readObject();
        return session;
    }
}
