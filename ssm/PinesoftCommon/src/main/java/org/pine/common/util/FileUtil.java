package org.pine.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang3.StringUtils;

/**
 * @author xier:
 * @Description:
 * @date 创建时间：2016年11月15日 下午3:51:52
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class FileUtil {

	/**
	 * 创建目录
	 * 
	 * @param dir
	 *            目录
	 */
	public static void mkdir(String dir) {
		try {
			String dirTemp = dir;
			File dirPath = new File(dirTemp);
			if (!dirPath.exists()) {
				dirPath.mkdirs();
			}
		}
		catch (Exception e) {
			LogUtil.error("创建目录操作出错: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 新建文件
	 * 
	 * @param fileName
	 *            String 包含路径的文件名 如:E:\phsftp\src\123.txt
	 * @param content
	 *            String 文件内容
	 * 
	 */
	public static void createNewFile(String fileName, String content) {
		try {
			String fileNameTemp = fileName;
			File filePath = new File(fileNameTemp);
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
		catch (Exception e) {
			LogUtil.error("新建文件操作出错: " + e.getMessage());
			e.printStackTrace();
		}

	}

	public static boolean isExist(String fileName) {
		try {
			String fileNameTemp = fileName;
			File filePath = new File(fileNameTemp);
			return filePath.exists();

		}
		catch (Exception e) {
			LogUtil.error(e.getMessage());
			e.printStackTrace();
		}
		return true;

	}

	/**
	 * 删除文件
	 * 
	 * @param fileName
	 *            包含路径的文件名
	 */
	public static void delFile(String fileName) {
		try {
			String filePath = fileName;
			java.io.File delFile = new java.io.File(filePath);
			delFile.delete();
		}
		catch (Exception e) {
			LogUtil.error("删除文件操作出错: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件夹
	 * 
	 * @param folderPath
	 *            文件夹路径
	 */
	public static void delFolder(String folderPath) {
		try {
			// 删除文件夹里面所有内容
			delAllFile(folderPath);
			String filePath = folderPath;
			java.io.File myFilePath = new java.io.File(filePath);
			// 删除空文件夹
			myFilePath.delete();
		}
		catch (Exception e) {
			LogUtil.error("删除文件夹操作出错" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件夹里面的所有文件
	 * 
	 * @param path
	 *            文件夹路径
	 */
	public static void delAllFile(String path) {
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
			}
			else {
				temp = new File(path + File.separator + childFiles[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + childFiles[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + childFiles[i]);// 再删除空文件夹
			}
		}
	}

	/**
	 * 复制单个文件
	 * 
	 * @param srcFile
	 *            包含路径的源文件 如：E:/phsftp/src/abc.txt
	 * @param dirDest
	 *            目标文件目录；若文件目录不存在则自动创建 如：E:/phsftp/dest
	 * @throws IOException
	 */
	public static void copyFile(String srcFile, String dirDest) {
		try {
			FileInputStream in = new FileInputStream(srcFile);
			mkdir(dirDest);
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
		catch (Exception e) {
			LogUtil.error("复制文件操作出错:" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 复制文件夹
	 * 
	 * @param oldPath
	 *            String 源文件夹路径 如：E:/phsftp/src
	 * @param newPath
	 *            String 目标文件夹路径 如：E:/phsftp/dest
	 * @return boolean
	 */
	public static void copyFolder(String oldPath, String newPath) {
		try {
			// 如果文件夹不存在 则新建文件夹
			mkdir(newPath);
			File file = new File(oldPath);
			String[] files = file.list();
			File temp = null;
			for (int i = 0; i < files.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + files[i]);
				}
				else {
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
		catch (Exception e) {
			LogUtil.error("复制文件夹操作出错:" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 移动文件到指定目录 ：如果指定目录有相同的文件名，就会覆盖
	 * 
	 * @param oldPath
	 *            包含路径的文件名 如：E:/phsftp/src/ljq.txt
	 * @param newPath
	 *            目标文件目录 如：E:/phsftp/dest
	 */
	public static void moveFile(String oldPath, String newPath) {
		copyFile(oldPath, newPath);
		delFile(oldPath);
	}

	/**
	 * 移动文件到指定目录，不会删除文件夹
	 * 
	 * @param oldPath
	 *            源文件目录 如：E:/phsftp/src
	 * @param newPath
	 *            目标文件目录 如：E:/phsftp/dest
	 */
	public static void moveFiles(String oldPath, String newPath) {
		copyFolder(oldPath, newPath);
		delAllFile(oldPath);
	}

	/**
	 * 移动文件到指定目录，会删除文件夹
	 * 
	 * @param oldPath
	 *            源文件目录 如：E:/phsftp/src
	 * @param newPath
	 *            目标文件目录 如：E:/phsftp/dest
	 */
	public static void moveFolder(String oldPath, String newPath) {
		copyFolder(oldPath, newPath);
		delFolder(oldPath);
	}

	/**
	 * 解压zip文件
	 * 
	 * @param srcDir
	 *            解压前存放的目录
	 * @param destDir
	 *            解压后存放的目录
	 * @throws Exception
	 */
	public static void Zip(String srcDir, String destDir) throws Exception {
		int leng = 0;
		byte[] b = new byte[1024 * 2];
		File[] zipFiles = new File(destDir).listFiles();
		if (zipFiles != null && !"".equals(zipFiles)) {
			for (int i = 0; i < zipFiles.length; i++) {
				if (zipFiles[i].getName().toUpperCase().indexOf(".ZIP") <= 0) break;
				File file = zipFiles[i];
				/** 解压的输入流 * */
				ZipInputStream zis = new ZipInputStream(new FileInputStream(file));
				ZipEntry entry = null;
				while ((entry = zis.getNextEntry()) != null) {
					File destFile = null;
					if (destDir.endsWith(File.separator)) {
						destFile = new File(destDir + entry.getName());
					}
					else {
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

	/**
	 * 压缩文件
	 * 
	 * @param srcDir
	 *            压缩前存放的目录
	 * @param destDir
	 *            压缩后存放的目录
	 * @throws Exception
	 */
	public static void UnZip(String srcDir, String destDir) throws Exception {
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
					}
					else {
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

	/**
	 * 压缩文件或文件夹成zip-可用
	 * 
	 * @param srcDir
	 *            压缩文件夹路径 或文件路径 最后不要加后缀\\
	 * @param destDir
	 *            E:\\yangs\\GDDK.zip 压缩文件输出流
	 * @param KeepDirStructure
	 *            是否保留原来的目录结构,true:保留目录结构;
	 *            false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @throws RuntimeException
	 *             压缩失败会抛出运行时异常
	 */

	public static void zip(String srcDir, String destDir, boolean KeepDirStructure) throws RuntimeException {
		// System.out.println("压缩中....");

		// long start = System.currentTimeMillis();
		ZipOutputStream zos = null;
		try {
			FileOutputStream fos = new FileOutputStream(new File(destDir));
			zos = new ZipOutputStream(fos);
			File sourceFile = new File(srcDir);
			compress(sourceFile, zos, sourceFile.getName(), KeepDirStructure);
			zos.close();
			// System.out.println("压缩..完成");
			// long end = System.currentTimeMillis();
			// System.out.println("压缩耗时：" + (end - start) + " ms");
		} catch (Exception e) {
			throw new RuntimeException("zip error from ZipUtils", e);
		} finally {
			if (zos != null) {
				try {
					zos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * 递归压缩方法
	 * 
	 * @param sourceFile
	 *            源文件
	 * @param zos
	 *            zip输出流
	 * @param name
	 *            压缩后的名称
	 * @param KeepDirStructure
	 *            是否保留原来的目录结构,true:保留目录结构;
	 *            false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @throws Exception
	 */
	private static void compress(File sourceFile, ZipOutputStream zos, String name, boolean KeepDirStructure)
			throws Exception {
		byte[] buf = new byte[2 * 1024];

		if (sourceFile.isFile()) {
			// 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
			zos.putNextEntry(new ZipEntry(name));
			// copy文件到zip输出流中
			int len;
			FileInputStream in = new FileInputStream(sourceFile);
			while ((len = in.read(buf)) != -1) {
				zos.write(buf, 0, len);
			}
			// Complete the entry
			zos.closeEntry();
			in.close();
		} else {
			File[] listFiles = sourceFile.listFiles();
			if (listFiles == null || listFiles.length == 0) {
				// 需要保留原来的文件结构时,需要对空文件夹进行处理
				if (KeepDirStructure) {
					// 空文件夹的处理
					zos.putNextEntry(new ZipEntry(name + "/"));
					// 没有文件，不需要文件的copy
					zos.closeEntry();
				}

			} else {
				for (File file : listFiles) {
					// 判断是否需要保留原来的文件结构
					if (KeepDirStructure) {
						// 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
						// 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
						compress(file, zos, name + "/" + file.getName(), KeepDirStructure);
					} else {
						compress(file, zos, file.getName(), KeepDirStructure);
					}

				}
			}
		}
	}

	/**
	 * 解压Zip压缩文件 - 可用
	 * 
	 * @param srcDir
	 *            压缩包路径
	 * @param descDir
	 *            解压路径
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Boolean UnZipFile(String srcDir, String descDir) {
		if (StringUtils.endsWith(descDir, "\\") == false) {
			descDir += "\\";
		}
		Boolean re = false;
		try {
			File filedesc = new File(descDir);
			if (!filedesc.exists()) {
				filedesc.mkdirs();
			}
			ZipFile zip = new ZipFile(srcDir);
			for (Enumeration entries = zip.entries(); entries.hasMoreElements();) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				String zipEntryName = entry.getName();
				InputStream ins = zip.getInputStream(entry);
				String outPath = (descDir + zipEntryName).replaceAll("\\*", "/");
				File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
				if (!file.exists()) {
					file.mkdirs();
				}
				if (new File(outPath).isDirectory()) {
					continue;
				}
				System.out.println(outPath);
				OutputStream out = new FileOutputStream(outPath);
				byte[] b = new byte[1024];
				int len;
				while ((len = ins.read(b)) > 0) {
					out.write(b, 0, len);
				}
				ins.close();
				out.close();
			}
			zip.close();
			re = true;
		}
		catch (Exception e) {
			e.printStackTrace();
			re = false;
		}
		return re;
	}

	/**
	 * 解压Zip压缩文件 - 可用
	 * 
	 * @param srcDir
	 *            压缩包路径
	 * @param descDir
	 *            解压路径
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Boolean unZipFile(String srcDir, String descDir) {
		if (StringUtils.endsWith(descDir, "\\") == false) {
			descDir += "\\";
		}
		// String folder= getFolder(srcDir);
		Boolean re = false;
		try {
			File filedesc = new File(descDir);
			if (!filedesc.exists()) {
				filedesc.mkdirs();
			}
			ZipFile zip = new ZipFile(srcDir);
			// 后面带//的文件夹
			// String folderPath="";
			// int i=0;
			for (Enumeration entries = zip.entries(); entries.hasMoreElements();) {
				// i+=1;
				ZipEntry entry = (ZipEntry) entries.nextElement();
				/*
				 * if(i==1){ if( entry.getName().replaceAll("/", "").equals(folder)==false){ String path=
				 * descDir+"\\"+folder; folderPath=folder+"\\"; if(FileUtil.isExist(path)==false){ FileUtil.mkdir(path);
				 * } } }
				 */
				String zipEntryName = entry.getName();
				InputStream ins = zip.getInputStream(entry);
				String outPath = (descDir + zipEntryName).replaceAll("\\\\", "/");
				File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
				if (!file.exists()) {
					file.mkdirs();
				}
				if (new File(outPath).isDirectory()) {
					continue;
				}
				System.out.println(outPath);
				OutputStream out = new FileOutputStream(outPath);
				byte[] b = new byte[1024];
				int len;
				while ((len = ins.read(b)) > 0) {
					out.write(b, 0, len);
				}
				ins.close();
				out.close();
			}
			zip.close();
			re = true;
		}
		catch (Exception e) {
			e.printStackTrace();
			re = false;
		}
		return re;
	}

	/***
	 * 获取文件夹名
	 * 
	 * @Title: getFolder
	 * @Description:
	 * @author yangs
	 * @param filePath
	 *            压缩文件路径
	 * @param @return
	 */
	/*
	 * static String getFolder(String filePath){ String[] paths=
	 * filePath.split("\\\\"); return paths[paths.length-1].split("\\.")[0]; }
	 */

	/***
	 * 解压zip格式文件：已测试，可用 有缺陷，压缩文件里有文件夹时，解压出来的文件有问题
	 * 
	 * @Title: unZip
	 * @Description:
	 * @author yangs
	 * @param filePath
	 *            文件路径
	 * @param unPath
	 *            解压路径
	 * @param cover
	 *            是否覆盖
	 * @param @throws
	 *            Exception
	 */
	public static void unZip(String filePath, String unPath, Boolean cover) throws Exception {

		if (!StringUtils.endsWith(unPath, "\\")) {
			unPath = unPath + "\\";
		}
		File f = new File(filePath);
		FileInputStream fis = new FileInputStream(f);
		ZipInputStream zis = new ZipInputStream(fis);
		new File(unPath).mkdirs();
		ZipEntry theEntry;
		while ((theEntry = zis.getNextEntry()) != null) {
			String fileName = theEntry.getName();
			new File((unPath + fileName).replace(new File(unPath + fileName).getName(), "")).mkdirs();
			if (fileName != "") {
				if ((new File(unPath + fileName).exists() && cover) || (!new File(unPath + fileName).exists())) {
					FileOutputStream fs = new FileOutputStream(unPath + fileName);
					int size = 2048;
					byte[] data = new byte[2048];
					while (true) {
						size = zis.read(data, 0, data.length);
						if (size > 0)
							fs.write(data, 0, size);
						else
							break;
					}
					fs.close();
				}
			}
		}
		zis.close();
	}

	/**
	 * 读取数据
	 * 
	 * @param inSream
	 * @param charsetName
	 * @return
	 * @throws Exception
	 */
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

	/**
	 * 一行一行读取文件，适合字符读取，若读取中文字符时会出现乱码
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
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

	/**
	 * 读取txt文件的内容
	 * 
	 * @param file
	 *            想要读取的文件对象
	 * @return 返回文件内容
	 */
	public static String file2String(String path) {
		File file = new File(path);
		StringBuilder result = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));// 构造一个BufferedReader类来读取文件
			String s = null;
			while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
				result.append(System.lineSeparator() + s);
			}
			br.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	/**
	 * 读取txt文件的内容
	 * 
	 * @param file
	 *            想要读取的文件对象
	 * @return 返回文件内容
	 */
	public static String file2String(File file) {
		StringBuilder result = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));// 构造一个BufferedReader类来读取文件
			String s = null;
			while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
				result.append(System.lineSeparator() + s);
			}
			br.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	/***
	 *  获取文件名-带后缀“.”
	 * 
	 * @Title: getFileSuffixByFileName
	 * @Description:
	 * @author yangs
	 * @param fileName
	 *            文件名-带后缀名
	 * @param @return
	 */
	public static String getFileSuffixByFileName(String fileName) {
		if (fileName.contains(".") == false) {
			return "";
		}
		String[] names = fileName.split("\\.");
		return "." + names[names.length - 1];
	}

	/***
	 * 获取文件名-不带后缀
	 * 
	 * @Title: getFileNameByFileName
	 * @Description:
	 * @author yangs
	 * @param fileName
	 *            完整文件名-带后缀
	 * @param @return
	 */
	public static String getFileNameByFileName(String fileName) {
		if (fileName.contains(".") == false) {
			return fileName;
		}
		String[] names = fileName.split("\\.");
		String result = "";
		for (int i = 0; i < names.length; i++) {
			if (i != (names.length - 1)) {
				if (result.equals("") == false) {
					result += ".";
				}
				result += names[i];
			}
		}
		return result;
	}

	/***
	 * 获取文件的后缀名
	 * 
	 * @Title: getSuffixName
	 * @Description:
	 * @author yangs
	 * @param @param
	 *            fileName 带后缀的文件名
	 * @param @return
	 */
	public static String getSuffixName(String fileName) {
		if (StringUtils.isEmpty(fileName)) {
			return "";
		}
		String[] names = fileName.split("\\.");
		return names[names.length - 1];
	}

	/***
	 * 获取文件字节数组
	 * 
	 * @Title: getStream
	 * @Description:
	 * @author yangs
	 * @param @param
	 *            path 完整路径
	 * @param @return
	 * @throws IOException
	 */
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
