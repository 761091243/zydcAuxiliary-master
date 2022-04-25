package com.wangpeng.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Dunn
 */
public class MyHttpServletUtil {

	private final static Logger logger = LoggerFactory.getLogger(MyHttpServletUtil.class);

	private static final String HEADER1 = "x-forwarded-for";
	private static final String HEADER2 = "Proxy-Client-IP";
	private static final String HEADER3 = "WL-Proxy-Client-IP";
	private static final String HEADER4 = "HTTP_CLIENT_IP";
	private static final String HEADER5 = "HTTP_X_FORWARDED_FOR";
	private static final String UNKNOWN = "unknown";

	/**
	 * 从request请求头获取真实IP
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader(HEADER1);
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader(HEADER2);
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader(HEADER3);
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader(HEADER4);
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader(HEADER5);
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 将request的完整路径和参数用get形式打印出来
	 * 
	 * @param request
	 * @return
	 */
	public static void logRequestURI(HttpServletRequest request, String body) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(request.getRequestURL());
		final Map<String, String[]> params = request.getParameterMap();
		Map<String, String> convParams = new HashMap<>(params.size());
		int flag = 0;
		for (Map.Entry<String, String[]> entry : params.entrySet()) {
			if (flag == 0) {
				strBuilder.append("?");
			} else {
				strBuilder.append("&");
			}
			String key = entry.getKey();
			String values = StringUtils.join(entry.getValue(), ",");
			convParams.put(key, values);
			strBuilder.append(key).append("=").append(values);
			flag++;
		}
		String remoteIP = getIpAddress(request);
		String requestMethod = request.getMethod();
		logger.info("IP[{}], Method[{}], {}, body[{}]", remoteIP, requestMethod, strBuilder.toString(), body);
	}



	/**
	 * 打印包装类中的json，并返回
	 * 
	 * @param wrapper
	 * @return
	 */
	public static String logResponseBody(MyHttpServletResponseWrapper wrapper) {
	//	String formatBody = MyUtil.delSpecialChar(wrapper.getRequestBody());
		return wrapper.getRequestBody();
	}

	/**
	 * 将字符串输出
	 * 
	 * @param responseBody
	 * @param response
	 * @throws IOException
	 */
	public static void writeResponseBody(String responseBody, HttpServletResponse response) throws IOException {
		response.setContentType("application/json;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(responseBody);
		printWriter.flush();
	}

}
