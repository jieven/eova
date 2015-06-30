package com.eova.common.utils.string;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
/**
 * 文件下载工具类
 * @author tlq
 *
 */
public class DownloadUtils {
//	private static final Logger log = LoggerFactory.getLogger(DownloadUtils.class);
	/**
	 * 下载文件到本地
	 * 
	 * @param urlString
	 *            被下载的文件地址
	 * @param filename
	 *            本地文件名
	 * @throws Exception
	 *             各种异常
	 */
	public static void download(String urlString, String filename)
			throws ConnectException,Exception {
		// 构造URL
		URL url = new URL(urlString);
		// 打开连接
		URLConnection con = url.openConnection();
		// 输入流
		InputStream is = con.getInputStream();
		// 1K的数据缓冲
		byte[] bs = new byte[1024];
		// 读取到的数据长度
		int len;
		// 输出的文件流
		OutputStream os = new FileOutputStream(filename);
		// 开始读取
		while ((len = is.read(bs)) != -1) {
			os.write(bs, 0, len);
		}
		// 完毕，关闭所有链接
		os.close();
		is.close();
	}
}
