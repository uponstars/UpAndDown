package com.starofdream.Servlet;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * Servlet implementation class DownloadServlet
 */
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String fileName = request.getParameter("fileName");
		
		//设置响应头
		response.addHeader("content-Type", "application/octet-stream");
		
		//根据请求头信息确定浏览器
		String agent = request.getHeader("User-Agent");
		if (agent.toLowerCase().contains("edge")) {
			response.addHeader("content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
		} else {
			response.addHeader("content-Disposition", "attachment;fileName=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");
		}
		
		//通过文件地址，将文件通过输入流读到servlet中
		String path = "/res/" + fileName;
		InputStream in = getServletContext().getResourceAsStream(path);
		
		//通过输出流将文件输出给用户
		ServletOutputStream out = response.getOutputStream();
		byte[] bs = new byte[16];
		int len = -1;
		while ((len = in.read(bs)) != -1) {
			out.write(bs, 0, len);
		}
		out.close();
		in.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
