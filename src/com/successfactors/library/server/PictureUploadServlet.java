package com.successfactors.library.server;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class PictureUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static final String UPLOAD_PATH = "D:\\Dev\\Tomcat 7.0\\webapps\\ROOT\\images\\upload\\";
	public static final String TEMP_PATH = "D:\\Dev\\Tomcat 7.0\\webapps\\ROOT\\images\\upload\\temp\\";

	@SuppressWarnings("rawtypes")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		System.out.println("开始上传图片");
		
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();
		String picNewName = getKey("IMG", 1);

		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload sevletFileUpload = new ServletFileUpload(factory);
			sevletFileUpload.setSizeMax(4 * 1024 * 1024);

			factory.setSizeThreshold(4096);
			factory.setRepository(new File(TEMP_PATH));

			List fileItems = sevletFileUpload.parseRequest(request);
			Iterator iter = fileItems.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (!item.isFormField()) {
					// 存入文件
					String name = item.getName();
					long size = item.getSize();
					if ((name == null || name.equals("")) && size == 0) {
						continue;
					}
					item.write(new File(UPLOAD_PATH + picNewName));
				}
			}
		} catch (FileUploadException e) {
			out.println(e);
			System.out.print(e);
		} catch (Exception e) {
			out.println(e);
			System.out.print(e);
		}
		
		out.print(picNewName);
		out.close();

		System.out.println("上传图片完成");

	}

	public static String getKey(String head, int tail) {
		return (head + String.valueOf((new Date()).getTime()) + String.format(
				"%03d", tail));
	}

}