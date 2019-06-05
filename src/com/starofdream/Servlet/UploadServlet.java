package com.starofdream.Servlet;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class UploadServlet
 */
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		//上传
			try {
				boolean isMultipart = ServletFileUpload.isMultipartContent(request);
				if (isMultipart) { //判断前台的form是否有multipart属性
					FileItemFactory factory = new DiskFileItemFactory();
					ServletFileUpload upload = new ServletFileUpload(factory);
					String path = request.getSession().getServletContext().getRealPath("upload");
					
					//设置缓冲区大小
					((DiskFileItemFactory) factory).setSizeThreshold(10240);
					//设置临时目录
					((DiskFileItemFactory) factory).setRepository(new File(path));
					
					//控制单个文件大小
					upload.setFileSizeMax(204800);
					
					List<FileItem> items = upload.parseRequest(request);
					Iterator<FileItem> iter = items.iterator();
					while (iter.hasNext()) {
						FileItem item = iter.next();
						String itemName = item.getFieldName();
						int sno = -1;
						String sname = null;
						
						//判断前台传来的字段是普通form表单字段还是文件字段
						if (item.isFormField()) {  //sno、sname
							if (itemName.equals("sno")) {
								sno = Integer.parseInt(item.getString("UTF-8"));
							} else {
								sname = item.getString("UTF-8");
							}
						} else {  //spicture
							//拿文件名
							String fileName = item.getName();
							
							//控制文件类型
							String ext = fileName.substring(fileName.indexOf(".") + 1);
							if (!(ext.equals("png") || ext.equals("gif") || ext.equals("jpg"))) {
								System.out.println("图片上传类型有误！只能是png/jpg/gif格式");
								return;
							}
							
							//获取文件内容,定义文件路径，指定上传位置，并上传
							//获取服务器路径
							File file = new File(path, fileName);
							item.write(file);
							
							System.out.println(fileName + "上传成功！");
							return;
						}
					}
				}
			} catch (FileUploadBase.FileSizeLimitExceededException e) {
				System.out.println("上传文件大小超出限制！最大200KB！");
				e.printStackTrace();
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
