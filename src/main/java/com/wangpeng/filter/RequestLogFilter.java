package com.wangpeng.filter;

import com.wangpeng.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/*
 * @author marvin
 *
 */
@Order(Integer.MIN_VALUE)
@Component
public class RequestLogFilter extends OncePerRequestFilter {

	private final static Logger logger = LoggerFactory.getLogger(RequestLogFilter.class);

	private ThreadLocal<Long> beginTime = new ThreadLocal<Long>();


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		beginTime.set(Long.valueOf(System.currentTimeMillis()));

		Thread.currentThread().setName(MyGuidUtil.getGUIDWithTime());
		MyHttpServletRequestWrapper wrapper1 = new MyHttpServletRequestWrapper(request);
		MyHttpServletResponseWrapper wrapper2 = new MyHttpServletResponseWrapper(response);
		String uri = wrapper1.getRequestURI();
		String requestBody = MyUtil.delSpecialChar(wrapper1.getRequestBody());
		// 打印请求地址
		MyHttpServletUtil.logRequestURI(wrapper1, requestBody);
		// 执行其他方法
		filterChain.doFilter(wrapper1, wrapper2);
		// 打印body
		String responseBody = MyHttpServletUtil.logResponseBody(wrapper2);
//		// 输出
		MyHttpServletUtil.writeResponseBody(responseBody, response);
		// 业务执行完打印耗时
		long endTime = System.currentTimeMillis();
		HashMap<String, String> map = new HashMap<>();
		uri = uri.replaceAll("[0-9]{2,64}","xxx");
		logger.info("URI:[{}], RESPONSE:[{}], cost:[{}]ms", uri, responseBody, endTime - beginTime.get().longValue());


	}
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		boolean result = new AntPathMatcher().match("/static/**", request.getServletPath());
		boolean result2 = new AntPathMatcher().match("/index/**", request.getServletPath());

		return result||result2;
	}

}
