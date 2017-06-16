package cn.xt.crawler.utils;

import java.util.List;

public class CommonUtils {
	
	public static boolean isEmptyList(@SuppressWarnings("rawtypes") List list){
		return list==null || list.isEmpty();
	}
	public static String remvoeSpareSymbol(String str,String symbol){
		if(str==null || str.trim().equals("") || !str.contains(symbol)){
			return "";
		}
		if(symbol==null || symbol.trim().equals("")){
			return "";
		}
		return str.substring(0,str.length()-symbol.length());
	}
}
