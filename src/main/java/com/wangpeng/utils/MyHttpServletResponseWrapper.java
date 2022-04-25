package com.wangpeng.utils;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * request的包装类，支持多次获取body中的数据
 * 
 * @author Marvin
 *
 */
public class MyHttpServletResponseWrapper extends HttpServletResponseWrapper {

	private ByteArrayOutputStream baos;
	private PrintWriter pw;

	public MyHttpServletResponseWrapper(HttpServletResponse response) throws IOException {
		super(response);
		baos = new ByteArrayOutputStream();
		pw = new PrintWriter(baos);
	}

	public String getRequestBody() {
		return baos.toString();
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return pw;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return new MyServletOutputStream();
	}

	class MyServletOutputStream extends ServletOutputStream {

		@Override
		public void write(int b) throws IOException {
			baos.write(b);
		}

		@Override
		public boolean isReady() {
			return false;
		}

		@Override
		public void setWriteListener(WriteListener listener) {

		}

	}

}
