package com.wangpeng.utils;

import java.io.*;

public class MyIOUtil {

	/**
	 * 将输入流的信息读取到Byte[]
	 * 
	 * @param inputStream
	 * @throws IOException
	 */
	public static byte[] readInputStream(InputStream inputStream) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] buff = new byte[1024 * 10];
		int rc = 0;
		while ((rc = inputStream.read(buff)) > 0) {
			byteArrayOutputStream.write(buff, 0, rc);
		}
		closeStreamQuiet(inputStream);
		return byteArrayOutputStream.toByteArray();
	}

	public static void closeStreamQuiet(Closeable stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				// ignore
			}
		}
	}

	/**
	 * 对象序列化
	 * 
	 * @param object
	 * @return
	 */
	public static byte[] serialize(Object object) {
		byte[] bytes = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			bytes = baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bytes;
	}

	/**
	 * 反序列化
	 * 
	 * @param bytes
	 * @return
	 */
	public static Object unserialize(byte[] bytes) {
		if(bytes == null) return null;
		Object object = null;
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			object = ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

}
