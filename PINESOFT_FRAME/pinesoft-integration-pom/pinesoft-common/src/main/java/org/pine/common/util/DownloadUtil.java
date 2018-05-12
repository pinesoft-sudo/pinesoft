package org.pine.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadUtil {

	public static void downloadFile(HttpServletResponse response, HttpServletRequest request, String path,
			String fileName) throws IOException {
		if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
			fileName = URLEncoder.encode(fileName, "UTF-8");
		} else {
			fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
		}
		// 设置响应头和客户端保存文件名
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);

		// 打开本地文件流
		InputStream inputStream = new FileInputStream(path);
		// 激活下载操作
		OutputStream os = response.getOutputStream();

		// 循环写入输出流
		byte[] b = new byte[inputStream.available()];
		int length;
		while ((length = inputStream.read(b)) > 0) {
			os.write(b, 0, length);
		}
		os.close();
		inputStream.close();

	}

	public static HttpServletResponse download(HttpServletResponse response, String path) throws IOException {
		File file = new File(path);
		// 取得文件名。
		String filename = file.getName();
		// 以流的形式下载文件。
		InputStream fis = new BufferedInputStream(new FileInputStream(path));
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();

		// 清空response
		response.reset();
		// 设置response的Header
		response.setContentType("application/octet-stream; charset=utf-8");
		response.addHeader("Content-Disposition",
				"attachment;filename=" + new String(filename.getBytes("UTF-8"), "ISO_8859_1"));
		response.addHeader("Content-Length", "" + file.length());
		OutputStream os = new BufferedOutputStream(response.getOutputStream());
		response.setContentType("application/octet-stream");

		os.write(buffer);
		os.flush();
		os.close();
		return response;
	}

}
