package cn.xt.crawler.utils;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlconnectionUtil {
	public static void downloadRes(String resUrl,String method, String path) {
		HttpURLConnection con = null;
		InputStream in = null;
		OutputStream out = null;

		try {
			URL url = new URL(resUrl);
			// 打开连接
			con = (HttpURLConnection) url.openConnection();
			con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setRequestMethod(method);
			in = con.getInputStream();
			out = new FileOutputStream(path);

			int len = 0;
			byte[] buf = new byte[1024 * 1024];
			while ((len = in.read(buf)) != -1) {
				out.write(buf, 0, len);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接
			try {
				if (in != null) {
					in.close();
					in = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			con.disconnect();
		}
	}
}
