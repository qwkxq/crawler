package cn.xt.crawler.rule;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Element;

public class SimpleUrlRule extends SimpleRule{
	private String url ; 
	private Map<String,String> data;
	private String query = "body";
	private int queryType;
	private int method;
	
	public SimpleUrlRule(String url){
		this.url = url;
	}
	public SimpleUrlRule(String url,String query,int queryType,int method){
		this.url = url;
		this.query = query;
		this.queryType = queryType;
		this.method = method;
	}
	
	public SimpleUrlRule(String url,Map<String,String> data,String query,int queryType,int method){
		this.url = url;
		Map<String,String> temp = new HashMap<String, String>();
		for (Map.Entry<String, String> map : data.entrySet()) {
			temp.put(map.getKey(), map.getValue());
		}
		this.data = temp;
		this.query = query;
		this.queryType = queryType;
		this.method = method;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Map<String, String> getData() {
		return data;
	}
	public void setData(Map<String, String> data) {
		this.data = data;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public int getMethod() {
		return method;
	}
	public void setMethod(int method) {
		this.method = method;
	}
	public int getQueryType() {
		return queryType;
	}
	public void setQueryType(int queryType) {
		this.queryType = queryType;
	}
}
