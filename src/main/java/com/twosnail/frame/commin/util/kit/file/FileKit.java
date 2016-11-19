package com.twosnail.frame.commin.util.kit.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Title: 此类中封装一些常用的文件操作。
 * Description:所有方法都是静态方法，不需要生成此类的实例,为避免生成此类的实例，构造方法被申明为private类型的。
 */
public class FileKit {

	/**
	 * 随机生成hash目录结构,默认每次生成的地址都是唯一的
	 * @param rootPath
	 * @param key
	 * @param deep
	 * @return
	 */
	public static String createHashPath( String rootPath, String key, int deep ) {
		return createHashPath( rootPath, key, deep, true );
	}
	/**
	 * 随机生成hash目录结构
	 * @param rootPath
	 * @param key
	 * @param deep
	 * @param unique - 是否每次生成的地址都是唯一的。
	 * @return
	 */
	@SuppressWarnings("null")
	public static String createHashPath( String rootPath, String key, int deep, boolean unique ) {

		String keys = null;
		if( unique ) {
			//keys = MD5.md5( key + System.currentTimeMillis() );
		} else {
			//keys = MD5.md5( key );
		}
		String dirPath = rootPath;
		if( deep > 0 ) {
			deep = Math.min( deep, 5 );
			for( int i = 0; i < deep; i++ ) {
				dirPath += "/" + keys.substring( i * 2, ( i + 1 ) * 2 );
			}
		}
		File dir = new File( dirPath );
		dir.mkdirs();
		return dirPath;
	}


	/**
	 * 创建文件，同时创建父级的文件夹
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static File createFile( String path ) throws IOException {
		path = path.replace( "\\", "/" );
		int lastIndex = path.lastIndexOf( '/' );
		String dir = path.substring( 0, lastIndex );
		File dirF = new File( dir );
		if( !dirF.exists() && !dirF.isDirectory() ) {
			dirF.mkdirs();
		}
		File f = new File( path );
		if( !f.exists() ) {
			f.createNewFile();
		}
		return f;
	}

	/**
	 * 将输入流复制到输出流
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	public static void copy( InputStream in, OutputStream out ) throws IOException {
		byte[] bs = new byte[2048];
		int length = 0;
		while( ( length = in.read( bs ) ) != -1 ) {
			out.write( bs, 0, length );
		}
	}

	/**
	 * 获得系统中文件夹的分隔符
	 *
	 * @return
	 */
	public static String getFileSeparator() {
		return System.getProperty("image.separator");
	}


	/**
	 * 删除指定路径的文件
	 *
	 * @param filePathName
	 *            String
	 * @return boolean
	 */
	public static boolean removeFile(String filePathName) {
		File file = new File(filePathName);
		if (file.isDirectory()) {
			String[] files = file.list();
			for (String string : files) {
				removeFile(string);
			}
			return file.delete();
		}

		if (file.isFile()) {
			return file.delete();
		}

		return true;
	}


	/**
	 * 创建指定的目录。
	 *
	 * 如果指定的目录的父目录不存在则创建其目录书上所有需要的父目录。
	 *
	 * <b>注意：可能会在返回false的时候创建部分父目录。</b>
	 *
	 * @param file
	 *            要创建的目录
	 * @return 完全创建成功时返回true，否则返回false。
	 */
	public static boolean makeDirectory(File file) {
		File parent = file.getParentFile();
		if (parent != null) {
			return parent.mkdirs();
		}
		return false;
	}

	/**
	 * 创建指定的目录。
	 *
	 * 如果指定的目录的父目录不存在则创建其目录书上所有需要的父目录。
	 *
	 * <b>注意：可能会在返回false的时候创建部分父目录。</b>
	 *
	 * @param fileName
	 *            要创建的目录的目录名
	 * @return 完全创建成功时返回true，否则返回false。
	 */
	public static boolean makeDirectory(String fileName) {
		File file = new File(fileName);
		return file.mkdirs();
		// return makeDirectory(image);
	}

	/**
	 * 清空指定目录中的文件。<br/>
	 * 这个方法将尽可能删除所有的文件，但是只要有一个文件没有被删除都会返回false。<br/>
	 *
	 * @param directory
	 *            要清空的目录
	 * @param iteration
	 *            true-迭代删除 false-不迭代删除
	 * @return 目录下的所有文件都被成功删除时返回true，否则返回false.
	 */
	public static boolean emptyDirectory(File directory, boolean iteration) {
		boolean result = false;
		File[] entries = directory.listFiles();
		for (int i = 0; i < entries.length; i++) {
			if (entries[i].isDirectory() && iteration) {
				emptyDirectory(entries[i], iteration);
			}
			if (!entries[i].delete()) {
				result = false;
			}
		}
		return result;
	}

	/**
	 * 从文件名得到文件绝对路径。
	 *
	 * @param fileName
	 *            文件名
	 * @return 对应的文件路径
	 */
	public static String getFilePath(String fileName) {
		File file = new File(fileName);
		return file.getAbsolutePath();
	}

	/**
	 * 将DOS/Windows格式的路径转换为UNIX/Linux格式的路径。
	 * 其实就是将路径中的"\"全部换为"/"，因为在某些情况下我们转换为这种方式比较方便，
	 * 某中程度上说"/"比"\"更适合作为路径分隔符，而且DOS/Windows也将它当作路径分隔符。
	 *
	 * @param filePath
	 *            转换前的路径
	 * @return 转换后的路径
	 */
	public static String toUNIXPath(String filePath) {
		return filePath.replaceAll("\\", "/");
	}

	/**
	 * 从文件名得到UNIX风格的文件绝对路径。
	 *
	 * @param fileName
	 *            文件名
	 * @return 对应的UNIX风格的文件路径
	 */
	public static String getUNIXfilePath(String fileName) {
		File file = new File(fileName);
		return toUNIXPath(file.getAbsolutePath());
	}

	/**
	 * 得到文件的类型(小写)。 实际上就是得到文件名中最后一个“.”后面的部分。
	 *
	 * @param fileName
	 *            文件名
	 * @return 文件名中的类型部分
	 */
	public static String getFilePostfix(String fileName) {
		int point = fileName.lastIndexOf('.');
		// 统一文件名称的格式，转换为小写的格式
		fileName = fileName.toLowerCase();
		int length = fileName.length();
		if (point == -1 || point == length - 1) {
			return "";
		} else {
			return fileName.substring(point + 1, length);
		}
	}

	/**
	 * 得到文件的类型。 实际上就是得到文件名中最后一个“.”后面的部分。
	 *
	 * @param file
	 *            文件
	 * @return 文件名中的类型部分
	 */
	public static String getFileType(File file) {
		return getFilePostfix(file.getName());
	}


	/**
	 * 得到路径分隔符在文件路径中首次出现的位置。 对于DOS或者UNIX风格的分隔符都可以。
	 *
	 * @param fileName
	 *            文件路径
	 * @return 路径分隔符在路径中首次出现的位置，没有出现时返回-1。
	 */
	public static int getPathIndex(String fileName) {
		String separator = getFileSeparator();
		return fileName.indexOf(separator);
	}

	/**
	 * 得到路径分隔符在文件路径中指定位置后首次出现的位置。
	 *
	 * 对于DOS或者UNIX风格的分隔符都可以。
	 *
	 * @param fileName
	 *            文件路径
	 * @param fromIndex
	 *            开始查找的位置
	 * @return 路径分隔符在路径中指定位置后首次出现的位置，没有出现时返回-1。
	 */
	public static int getPathIndex(String fileName, int fromIndex) {
		String separator = getFileSeparator();
		return fileName.indexOf(separator, fromIndex);
	}

	/**
	 * 得到路径分隔符在文件路径中最后出现的位置。 对于DOS或者UNIX风格的分隔符都可以。
	 *
	 * @param fileName
	 *            文件路径
	 * @return 路径分隔符在路径中最后出现的位置，没有出现时返回-1。
	 */
	public static int getPathLastIndex(String fileName) {
		String separator = getFileSeparator();
		return fileName.lastIndexOf(separator);
	}

	/**
	 * 得到路径分隔符在文件路径中指定位置前最后出现的位置。 对于DOS或者UNIX风格的分隔符都可以。
	 *
	 * @param fileName
	 *            文件路径
	 * @param fromIndex
	 *            开始查找的位置
	 * @return 路径分隔符在路径中指定位置前最后出现的位置，没有出现时返回-1。
	 */
	public static int getPathLastIndex(String fileName, int fromIndex) {
		String separator = getFileSeparator();
		return fileName.lastIndexOf(separator, fromIndex);
	}

	/**
	 * 将文件名中的类型部分去掉。
	 *
	 * @param filename
	 *            文件名
	 * @return 去掉类型部分的结果
	 */
	public static String trimType(String filename) {
		int index = filename.lastIndexOf(".");
		if (index != -1) {
			return filename.substring(0, index);
		} else {
			return filename;
		}
	}

	/**
	 * 得到相对路径。 文件名不是目录名的子节点时返回文件名。
	 *
	 * @param pathName
	 *            目录名
	 * @param fileName
	 *            文件名
	 * @return 得到文件名相对于目录名的相对路径，目录下不存在该文件时返回文件名
	 */
	public static String getSubpath(String pathName, String fileName) {
		int index = fileName.indexOf(pathName);
		if (index != -1) {
			return fileName.substring(index + pathName.length() + 1);
		} else {
			return fileName;
		}
	}

	/**
	 * 拷贝文件。如果目标文件存在默认不覆盖
	 *
	 * @param fromFileName
	 *            源文件名
	 * @param toFileName
	 *            目标文件名
	 * @return 成功生成文件时返回true，否则返回false
	 * @throws IOException
	 */
	public static boolean copy( String fromFileName, String toFileName ) throws IOException {
		return copy(fromFileName, toFileName, false);
	}

	/**
	 * 拷贝文件。
	 *
	 * @param fromFileName
	 *            源文件名
	 * @param toFileName
	 *            目标文件名
	 * @param override
	 *            目标文件存在时是否覆盖
	 * @return 成功生成文件时返回true，否则返回false
	 * @throws IOException
	 */
	public static boolean copy( String fromFileName, String toFileName, boolean override )
			throws IOException {

		File fromFile = new File(fromFileName);
		File toFile = new File(toFileName);

		if( !fromFile.exists() ) {
			throw new IOException( "源文件" + fromFileName + "不存在" );
		}
		if( !fromFile.isFile() ) {
			throw new IOException( "源文件" + fromFileName + "不是一个文件" );
		}
		if( !fromFile.canRead() ) {
			throw new IOException( "源文件" + fromFileName + "不能读取" );
		}

		if( toFile.isDirectory() ) {
			toFile = new File(toFile, fromFile.getName());

		}

		if( !toFile.exists() || !toFile.isFile() ) {
			toFile = createFile( toFileName );
		} else {
			if( override ) {
				//删除重新创建
				toFile.delete();
				toFile = createFile( toFileName );
			} else {
				//文件存在，不覆盖，则返回失败
				return false;
			}
		}

		FileInputStream from = null;
		FileOutputStream to = null;
		try {
			from = new FileInputStream(fromFile);
			to = new FileOutputStream(toFile);
			byte[] buffer = new byte[4096];
			int bytes_read;
			while ((bytes_read = from.read(buffer)) != -1) {
				to.write(buffer, 0, bytes_read);
			}
			return true;
		} finally {
			if (from != null) {
				try {
					from.close();
				} catch (IOException e) {
					;
				}
			}
			if (to != null) {
				try {
					to.close();
				} catch (IOException e) {
					;
				}
			}
		}
	}

	/**
	 * 得到文件大小，单位是B
	 *
	 * @param path
	 *            文件路径
	 * */
	public static float getFileSize(String path) {
		try {
			return Float.parseFloat(GetFileSize.fileSize(path)
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		} catch (Exception e) {
			return 0F;
		}

	}
}

class GetFileSize {
	@SuppressWarnings("resource")
	private BigDecimal getFileSizes(File f) throws Exception {// 取得文件大小
		BigDecimal s = new BigDecimal("0");
		if (f.exists()) {
			FileInputStream fis = null;
			fis = new FileInputStream(f);
			s = new BigDecimal(fis.available());
		} else {
			f.createNewFile();
			System.out.println("没找到文件");
		}
		return s;
	}

	// 递归
	private BigDecimal getFileSize(File f) throws Exception// 取得文件夹大小
	{
		BigDecimal size = new BigDecimal("0");
		File flist[] = f.listFiles();
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size.add(getFileSize(flist[i]));
			} else {
				size = size.add(new BigDecimal(flist[i].length() + ""));
			}
		}
		return size;
	}

	
	@SuppressWarnings("unused")
	private String FormetFileSize(long fileS) {// 转换文件大小
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	private long getlist(File f) {// 递归求取目录文件个数
		long size = 0;
		File flist[] = f.listFiles();
		size = flist.length;
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getlist(flist[i]);
				size--;
			}
		}
		return size;
	}

	/***
	 * 获取文件个数
	 * 
	 * @param path
	 *            文件夹或文件的路径。若是文件夹路径则返回的是文件夹下面有多少文件，若是文件路径则返回的是1
	 * */
	public static long fileCount(String path) {
		GetFileSize g = new GetFileSize();
		@SuppressWarnings("unused")
		long l = 0;

		File ff = new File(path);
		if (ff.isDirectory()) { // 如果路径是文件夹的时候
			return g.getlist(ff);
		} else {
			if (ff.exists()) {
				return 1L;
			}
		}
		return 0L;
	}

	/**
	 * 得到文件夹或文件大小
	 * 
	 * @param path
	 *            文件夹或文件的路径。若是文件夹路径则返回的是文件夹下所有文件大小总和，若是文件路径则返回的是单个文件大小
	 * @return 返回值大为 B
	 * */
	public static BigDecimal fileSize(String path) {
		if (fileCount(path) == 0) {
			return new BigDecimal("0");
		}
		GetFileSize g = new GetFileSize();
		try {
			BigDecimal l = new BigDecimal("0");
			File ff = new File(path);
			if (ff.isDirectory()) { // 如果路径是文件夹的时候
				l = g.getFileSize(ff);
				return l;// g.FormetFileSize(l);
			} else {
				l = g.getFileSizes(ff);
				return l;// g.FormetFileSize(l);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new BigDecimal("0");
	}

}
