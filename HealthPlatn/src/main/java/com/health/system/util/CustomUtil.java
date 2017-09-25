package com.health.system.util;

public class CustomUtil {
	
	/**
	 * 获取资讯的标题
	 * @param title
	 * @param time
	 * @param clickNum
	 * @return
	 */
	public static String getNewsHeader(String title, String imgPath, String time, String clickNum){
		String headstr = "<p><br/></p><p align=\"center\"><font size=\"6\" style=\"font-family:Microsoft YaHei,tahoma,arial,sans-serif;\">"+title+"</font></p><p align=\"center\"><font color=\"#999999\">"+time+"&nbsp;&nbsp;&nbsp;&nbsp;浏览次数:"+clickNum+"</font></p><p align=\"center\"><img src=\""+ imgPath +"\" width=\"280\" height=\"180\"></p><p><br/></p>";
		return headstr;
	}
	
	/**
	 * 获取消息的标题
	 * @param title
	 * @param time
	 * @param clickNum
	 * @return
	 */
	public static String getMsgHeader(String title, String imgPath, String time){
		String headstr = "<p><br/></p><p align=\"center\"><font size=\"6\" style=\"font-family:Microsoft YaHei,tahoma,arial,sans-serif;\">"+title+"</font></p><p align=\"center\"><font color=\"#999999\">"+time+"</font></p><p align=\"center\"><img src=\""+ imgPath +"\" width=\"280\" height=\"180\"></p><p><br/></p>";
		return headstr;
	}
	
	/**
	 * 获取产品的标题
	 * @param title
	 * @param time
	 * @param clickNum
	 * @return
	 */
	public static String getProductHeader(String name){
		String headstr = "<p><br/></p><p align=\"center\"><font size=\"6\" style=\"font-family:Microsoft YaHei,tahoma,arial,sans-serif;\">"+name+"</font></p><p><br/></p>";
		return headstr;
	}

}
