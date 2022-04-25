package com.wangpeng.utils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * request的包装类，支持多次获取body中的数据
 * 
 * @author Marvin
 *
 */
public class MyHttpServletRequestWrapper extends HttpServletRequestWrapper {

	private byte[] requestBody;

	public MyHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		requestBody = MyIOUtil.readInputStream(request.getInputStream());
	}

	public String getRequestBody() {
		return new String(requestBody);
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		return new MyServletInputStream();
	}

	class MyServletInputStream extends ServletInputStream {

		private ByteArrayInputStream bais = new ByteArrayInputStream(requestBody);

		@Override
		public int read() throws IOException {
			return bais.read();
		}

		@Override
		public boolean isFinished() {
			return false;
		}

		@Override
		public boolean isReady() {
			return false;
		}

		@Override
		public void setReadListener(ReadListener readListener) {

		}
	}

}
