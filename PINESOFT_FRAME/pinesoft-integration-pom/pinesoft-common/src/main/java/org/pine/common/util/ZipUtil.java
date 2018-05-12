package org.pine.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

	public static void unzipFile(String srcDir, String destDir) throws Exception {
		int leng = 0;
		byte[] b = new byte[1024 * 2];
		File[] zipFiles = new File(destDir).listFiles();
		if (zipFiles != null && zipFiles.length > 0) {
			for (int i = 0; i < zipFiles.length; i++) {
				if (zipFiles[i].getName().toUpperCase().indexOf(".ZIP") <= 0)
					break;
				File file = zipFiles[i];
				/** 解压的输入流 * */
				ZipInputStream zis = new ZipInputStream(new FileInputStream(file));
				ZipEntry entry = null;
				while ((entry = zis.getNextEntry()) != null) {
					File destFile = null;
					if (destDir.endsWith(File.separator)) {
						destFile = new File(destDir + entry.getName());
					} else {
						destFile = new File(destDir + "/" + entry.getName());
					}
					/** 把解压包中的文件拷贝到目标目录 * */
					FileOutputStream fos = new FileOutputStream(destFile);
					while ((leng = zis.read(b)) != -1) {
						fos.write(b, 0, leng);
					}
					fos.close();
				}
				zis.close();
			}
		}
	}

	public static void zipFile(String srcDir, String destDir) throws Exception {
		String tempFileName = null;
		byte[] buf = new byte[1024 * 2];
		int len;
		// 获取要压缩的文件
		File[] files = new File(srcDir).listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isFile()) {
					FileInputStream fis = new FileInputStream(file);
					BufferedInputStream bis = new BufferedInputStream(fis);
					if (destDir.endsWith(File.separator)) {
						tempFileName = destDir + file.getName() + ".zip";
					} else {
						tempFileName = destDir + "/" + file.getName() + ".zip";
					}
					FileOutputStream fos = new FileOutputStream(tempFileName);
					BufferedOutputStream bos = new BufferedOutputStream(fos);
					ZipOutputStream zos = new ZipOutputStream(bos);// 压缩包

					ZipEntry ze = new ZipEntry(file.getName());// 压缩包文件名
					zos.putNextEntry(ze);// 写入新的ZIP文件条目并将流定位到条目数据的开始处

					while ((len = bis.read(buf)) != -1) {
						zos.write(buf, 0, len);
						zos.flush();
					}
					bis.close();
					zos.close();

				}
			}
		}
	}
}
