package com.mypro.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

/**
 * 序列化工具
 * @author user
 *
 */
public class SerializationUtil {
	/**
	 * ProtoStuff 序列化
	 * @param obj 需要序列化的对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> byte[] protoStuffSerialize(T obj){
		if (obj == null) {
            throw new RuntimeException("序列化对象(" + obj + ")!");
        }
        Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(obj.getClass());
        LinkedBuffer buffer = LinkedBuffer.allocate(1024 * 1024);
        byte[] protostuff = null;
        try {
            protostuff = ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } catch (Exception e) {
            throw new RuntimeException("序列化(" + obj.getClass() + ")对象(" + obj + ")发生异常!", e);
        } finally {
            buffer.clear();
        }
        return protostuff;
	}
	/**
	 * ProtoStuff 反序列化
	 * @param arr
	 * @param clz 发序列化的目标类型
	 * @return
	 */
	public static <T> T protoStuffDeserialize(byte[] arr, Class<T> clz){
		if (arr == null || arr.length == 0) {
            throw new RuntimeException("反序列化对象发生异常,byte序列为空!");
        }
        T instance = null;
        try {
            instance = clz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("反序列化过程中依据类型创建对象失败!", e);
        } catch (IllegalAccessException  e) {
        	throw new RuntimeException("反序列化过程中依据类型创建对象失败!", e);
		}
        Schema<T> schema = RuntimeSchema.getSchema(clz);
        ProtostuffIOUtil.mergeFrom(arr, instance, schema);
        return instance;
	}
	/**
	 * ProtoStuff 序列化 集合
	 * @param list 需要序列化的集合
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> byte[] protoStuffSerializeList(List<T> list){
		if (list == null) {
            throw new RuntimeException("序列化对象(" + list + ")!");
        }
        Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(list.get(0).getClass());
        LinkedBuffer buffer = LinkedBuffer.allocate(1024 * 1024);
        byte[] protostuff = null;
        ByteArrayOutputStream bos = null;
        try {
        	bos = new ByteArrayOutputStream();
            ProtostuffIOUtil.writeListTo(bos, list, schema, buffer);
            protostuff = bos.toByteArray();
        } catch (Exception e) {
        	throw new RuntimeException("序列化对象列表(" + list + ")发生异常!", e);
        } finally {
            buffer.clear();
        }
        return protostuff;
	}
	/**
	 * ProtoStuff 反序列化 集合
	 * @param list
	 * @param clz 
	 * @return
	 */
	public static <T> List<T> protoStuffDeserializeList(byte[] arr, Class<T> clz){
		if (arr == null || arr.length == 0) {
            throw new RuntimeException("反序列化对象发生异常,byte序列为空!");
        }
        Schema<T> schema = RuntimeSchema.getSchema(clz);
        List<T> result = null;
        try {
            result = ProtostuffIOUtil.parseListFrom(new ByteArrayInputStream(arr), schema);
        } catch (IOException e) {
            throw new RuntimeException("反序列化对象列表发生异常!",e);
        }
        return result;
	}
	public static void main(String[] args) {
		/*QtTag tag = new QtTag();
		tag.setTag_name("测试");
		tag.setTag_num(100);
		QtTag t = null;
		t = protoStuffDeserialize(protoStuffSerialize(tag), QtTag.class);
		System.err.println(t.getTag_num());*/
	}
}
