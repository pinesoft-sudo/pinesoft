package org.pine.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

public class FileUtil {

	public static void createFolder(String path) throws IOException {
		File dirPath = new File(path);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
	}

	public static void createFile(String path, String content) throws IOException {
		File filePath = new File(path);
		if (!filePath.exists()) {
			filePath.createNewFile();
		}
		FileWriter fw = new FileWriter(filePath);
		PrintWriter pw = new PrintWriter(fw);
		String strContent = content;
		pw.println(strContent);
		pw.flush();
		pw.close();
		fw.close();
	}

	public static boolean isExist(String path) throws IOException {
		File filePath = new File(path);
		return filePath.exists();
	}

	public static void delFile(String path) throws IOException {
		File delFile = new File(path);
		delFile.delete();
	}

	public static void delFolder(String path) throws IOException {
		delFolderAndFile(path);
		File myFilePath = new File(path);
		myFilePath.delete();
	}

	private static void delFolderAndFile(String path) throws IOException {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] childFiles = file.list();
		File temp = null;
		for (int i = 0; i < childFiles.length; i++) {
			// File.separator与系统有关的默认名称分隔符
			// 在UNIX系统上，此字段的值为'/'；在Microsoft Windows系统上，它为 '\'。
			if (path.endsWith(File.separator)) {
				temp = new File(path + childFiles[i]);
			} else {
				temp = new File(path + File.separator + childFiles[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delFolderAndFile(path + "/" + childFiles[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + childFiles[i]);// 再删除空文件夹
			}
		}
	}

	public static void copyFile(String srcFile, String dirDest) throws IOException {
		FileInputStream in = new FileInputStream(srcFile);
		createFolder(dirDest);
		FileOutputStream out = new FileOutputStream(dirDest + "/" + new File(srcFile).getName());
		int len;
		byte buffer[] = new byte[1024];
		while ((len = in.read(buffer)) != -1) {
			out.write(buffer, 0, len);
		}
		out.flush();
		out.close();
		in.close();
	}

	public static void copyFolder(String oldPath, String newPath) throws IOException {
		createFolder(newPath);
		File file = new File(oldPath);
		String[] files = file.list();
		File temp = null;
		for (int i = 0; i < files.length; i++) {
			if (oldPath.endsWith(File.separator)) {
				temp = new File(oldPath + files[i]);
			} else {
				temp = new File(oldPath + File.separator + files[i]);
			}

			if (temp.isFile()) {
				FileInputStream input = new FileInputStream(temp);
				FileOutputStream output = new FileOutputStream(newPath + "/" + (temp.getName()).toString());
				byte[] buffer = new byte[1024 * 2];
				int len;
				while ((len = input.read(buffer)) != -1) {
					output.write(buffer, 0, len);
				}
				output.flush();
				output.close();
				input.close();
			}
			if (temp.isDirectory()) {// 如果是子文件夹
				copyFolder(oldPath + "/" + files[i], newPath + "/" + files[i]);
			}
		}
	}

	public static void moveFile(String oldPath, String newPath) throws IOException {
		copyFile(oldPath, newPath);
		delFile(oldPath);
	}

	public static void moveFolder(String oldPath, String newPath) throws IOException {
		copyFolder(oldPath, newPath);
		delFolder(oldPath);
	}

	public static String readData(InputStream inSream, String charsetName) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = inSream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inSream.close();
		return new String(data, charsetName);
	}

	public static Set<String> readFile(String path) throws Exception {
		Set<String> datas = new HashSet<String>();
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		String line = null;
		while ((line = br.readLine()) != null) {
			datas.add(line);
		}
		br.close();
		fr.close();
		return datas;
	}

	public static String file2String(String path) throws IOException {
		File file = new File(path);
		return file2String(file);
	}

	public static String file2String(File file) throws IOException {
		StringBuilder result = new StringBuilder();
			BufferedReader br = new BufferedReader(new FileReader(file));
			String s = null;
			while ((s = br.readLine()) != null) {
				result.append(System.lineSeparator() + s);
			}
			br.close();
		return result.toString();
	}

	public static String getSuffix(String fileName) {
		return "." + getSuffixName(fileName);
	}
	
	public static String getSuffixName(String fileName) {
		if (StringUtils.isEmpty(fileName)) {
			return "";
		}
		String[] names = fileName.split(".");
		return names[names.length - 1];
	}

	public static byte[] getBytes(String path) throws IOException {
		if (StringUtils.isEmpty(path)) {
			return null;
		}
		InputStream fis = new BufferedInputStream(new FileInputStream(path));
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		return buffer;
	}
}
