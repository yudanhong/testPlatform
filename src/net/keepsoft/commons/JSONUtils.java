package net.keepsoft.commons;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * JSON工具类
 * 需引用gson.jar 2.2.1+
 * @author zxd
 *
 */
public class JSONUtils {
	
	
	
	/**
	 * 把JSON对象转换成String
	 * @param object
	 * @return
	 */
	public static String toJSONString(Object object) {
		Gson gson = new Gson();
		return gson.toJson(object);
	}
	/**
	 *把JSON对象转换成String
	 * @param object
	 * @param pattern 时间格式 YYYY-MM-dd HH:mm
	 * @return
	 */
	public static String toJSONString(Object object,String pattern) {
		Gson gson = new GsonBuilder().setDateFormat(pattern).create();
		return gson.toJson(object);
	}
	
	/**
	 * 将文件里的JSON字符串转换实体对象
	 * @param file
	 * @param classOfT
	 * @return
	 * @throws IOException
	 */
	public static <T> T fromJSON(File file, Class<T> classOfT) throws IOException {
		if(!file.exists()) return null;
		String json = FileUtils.readFileToString(file,"utf-8");
		Gson gson = new Gson();
		return gson.fromJson(json, classOfT);
	}
	
	/**
	 * 把json字符串转换成实体对象
	 * @param json json字符串
	 * @param classOfT 实体对象
	 * @return
	 */
	public static <T> T fromJSON(String json, Class<T> classOfT) {
		Gson gson = new Gson();
		return gson.fromJson(json, classOfT);
	}
	
	
	 /**
	  * 把json字符串转换成成对象
	  * 如：List<entity> 则：Type为new TypeToken<List<entity>>() {}.getType()
	  * @param json
	  * @param typeOfT
	  * @return
	 * @throws IOException 
	  */
	public static <T> T fromJSON(File file, Type typeOfT) throws IOException {
		if(!file.exists()) return null;
		String json = FileUtils.readFileToString(file,"utf-8");
		Gson gson = new Gson();
		return gson.fromJson(json, typeOfT);
	}
	
	 /**
	  * 把json字符串转换成成对象
	  * 如：List<entity> 则：Type为new TypeToken<List<entity>>() {}.getType()
	  * @param json
	  * @param typeOfT
	  * @return
	  */
	public static <T> T fromJSON(String json, Type typeOfT) {
		Gson gson = new Gson();
		return gson.fromJson(json, typeOfT);
	}
//	
//	public static <T> T fromJSON(String json, Type typeOfT,String pattern) {
//		GsonBuilder gsonBuilder = new GsonBuilder();
//		Gson gson = gsonBuilder.registerTypeAdapter(java.util.Date.class,new DateDeserializerUtils(pattern)).create();
//		return gson.fromJson(json, typeOfT);
//	}
//	


}
