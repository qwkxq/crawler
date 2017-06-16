package cn.xt.crawler.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.xt.crawler.core.AbstractElement;
import cn.xt.crawler.core.ElementsInstance;
import cn.xt.crawler.entity.BaseResult;
import cn.xt.crawler.rule.SimpleUrlRule;
import cn.xt.crawler.service.ResultService;

public class Test {
	public static void main(String[] args) {
		
		//80s
		Map<String,String> params = new HashMap<String,String>();
		params.put("keyword", "夏洛特烦恼");
		SimpleUrlRule rule = new SimpleUrlRule("http://www.80s.tw/search", 
				params,"body",SimpleUrlRule.TAG,SimpleUrlRule.POST);
		
		ResultService service  = new ResultService();
		List<BaseResult> results = service.getNew80sResultsBySimpleRule(rule);
		for (BaseResult result : results) {
			System.out.println(result.getTitle());
			System.out.println(result.getText());
			System.out.println(result.getHref());
			System.out.println("*****************80s***************************");
		}
		
		System.out.println("------------------------------------------------");
		System.out.println("------------------------------------------------");
		 
		
		//baidusearch
		Map<String,String> params2 = new HashMap<String,String>();
		params2.put("pn", "0");
		params2.put("word", "java");
		SimpleUrlRule rule2 = new SimpleUrlRule("http://www.baidu.com/s", 
				params2,"body",SimpleUrlRule.TAG,SimpleUrlRule.GET);
		
		ResultService service2  = new ResultService();
		List<BaseResult> results2 = service2.getBaiduSearchResultsBySimpleRule(rule2);
		for (BaseResult result : results2) {
			System.out.println(result.getTitle());
			System.out.println(result.getText());
			System.out.println(result.getHref());
			System.out.println("*****************baidu***************************");
		}
	}
}
