package com.wangpeng.utils;

import com.alibaba.fastjson.JSON;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyUtil {

	/**
	 * 将String类型的私钥转化为PrivateKey对象
	 * 
	 * @param key
	 * @return
	 */
	public static PrivateKey convertStringToPrivateKey(String key) {
		PrivateKey privateKey = null;
		try {
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodeBase64String(key));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			privateKey = keyFactory.generatePrivate(keySpec);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return privateKey;
	}

	/**
	 * 将string格式的公钥转化为X509Certificate对象
	 * 
	 * @param key
	 * @return
	 */
	public static X509Certificate convertStringToX509Cert(String key) {
		X509Certificate cert = null;
		try {
			InputStream in = new ByteArrayInputStream(decodeBase64String(key));
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			cert = (X509Certificate) cf.generateCertificate(in);
			closeStreamQuiet(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cert;
	}

	/**
	 * 将base64的String转成byte
	 * 
	 * @param base64String
	 * @return
	 */
	public static byte[] decodeBase64String(String base64String) {
		return Base64.getDecoder().decode(base64String);
	}

	/**
	 * 从文件按行读取一个list
	 * 
	 * @param filePath
	 * @return
	 */
	public static List<String> readListFromFile(String filePath) {
		List<String> lines = new ArrayList<String>();
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			fis = new FileInputStream(filePath);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeStreamQuiet(br);
			closeStreamQuiet(isr);
			closeStreamQuiet(fis);
		}
		return lines;
	}

	private static void closeStreamQuiet(Closeable stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				// ignore
			}
		}
	}

	/**
	 * 在文件尾部追加一行内容
	 * 
	 * @param filePath
	 * @param content
	 * @return
	 */
	public static boolean appendContent(String filePath, String content) {
		boolean flag = false;
		FileWriter writer = null;
		try {
			if (content == null) {
				flag = true;
			} else {
				writer = new FileWriter(filePath, true);
				writer.write(content);
				writer.write("\r\n");
				flag = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			closeStreamQuiet(writer);
		}
		return flag;
	}

	/**
	 * 删除 \t|\r|\n 符号
	 * 
	 * @param content
	 * @return
	 */
	public static String delSpecialChar(String content) {
		String destination = null;
		if (content != null) {
			Pattern p = Pattern.compile("\t|\r|\n");
			Matcher m = p.matcher(content);
			destination = m.replaceAll("");
		}
		return destination;
	}



	public static<T> T converyToJavaBean(String xml,Class<T> c){
		T t = null;
		try {
			JAXBContext context = JAXBContext.newInstance(c);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			t = (T)unmarshaller.unmarshal(new StringReader(xml));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * 字符串转对象
	 * 
	 * @param dataStr
	 * @param clazz
	 * @return
	 */
	public static <T> T parseObject(String dataStr, Class<T> clazz) {
		T response = null;
		try {
			response = JSON.parseObject(dataStr, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * 实例化一个类，返回对象
	 * 
	 * @param clazz
	 * @return
	 */
	public static <T> T newInstance(Class<T> clazz) {
		T response = null;
		try {
			response = clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public static void main(String[] args) throws Exception {

	}

}
