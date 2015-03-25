package net.keepsoft.utils;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class CommonUtils {
	/**
	 * 根据路径创建本地文件夹
	 * @param path
	 * @throws IOException
	 */
	public static void mkDir(String path) throws IOException{
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
	}
	/**
	 * 获取时间格式
	 * @param formatString
	 * @return
	 */
	public static String getTime(String formatString){
		SimpleDateFormat format = new SimpleDateFormat(formatString);
		return format.format(new Date());
		
	}
	public static String getLocalIpAddress(){
		String ip = null;
		try {
			ip = InetAddress.getLocalHost().getHostName()+"/"+InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ip;
	}
	
	public static String snapshot(String photoBasePath,WebDriver driver){
		String photo = "";
		try { 
            File srcFile = ((TakesScreenshot)driver). getScreenshotAs(OutputType.FILE);
            photo = photoBasePath+"\\"+getTime("HHmmss")+".png";
            FileUtils.copyFile(srcFile,new File(photo));
        } catch (Exception e) {
            e.printStackTrace(); 
        }
		return photo.substring(photo.indexOf("snapshot"));
	}

}
