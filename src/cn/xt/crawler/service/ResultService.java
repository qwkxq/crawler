package cn.xt.crawler.service;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.xt.crawler.core.AbstractElement;
import cn.xt.crawler.entity.BaseResult;
import cn.xt.crawler.rule.SimpleUrlRule;
import cn.xt.crawler.utils.CommonUtils;

public class ResultService {

	/** 
	 * @Title: getNew80sResultsBySimpleRule 
	 * @Description: TODO(抓取80s) 
	 * @param @param rule
	 * @param @return 
	 * @return List<BaseResult> 
	 * @throws 
	 */ 
	public List<BaseResult> getNew80sResultsBySimpleRule(SimpleUrlRule rule){
		List<BaseResult> results = new ArrayList<BaseResult>();
		
		AbstractElement abstractElement = new AbstractElement() {
			@Override
			public String getTargetUrl(String prependUrl, Element target) {
				String targetUrl = "";
				if (target.attr("href").startsWith("http://")) {
					targetUrl = target.attr("href");
				} else {
					targetUrl = prependUrl.substring(0, prependUrl.lastIndexOf("/"))
							+ target.attr("href");
				}
				return targetUrl;
			}
		};
		Elements contents = new Elements();
		abstractElement.setTargetElemets2ResultsBySelectors(0, new String[]{
				".search_list li a",".cpdl2list a[rel='nofollow']"
		}, rule.getUrl(), contents, rule);
		
		BaseResult result = new BaseResult();
		List<String> linkList = new ArrayList<String>();
		
		for (Element target : contents) {
			if(target.attr("href").startsWith("thunder://") || target.attr("href").startsWith("magnet:?xt=")){
				linkList.add(target.attr("href"));
			}
		}
		result.setHref(linkList);
		results.add(result);
		
		return results;
	}
	
	/** 
	 * @Title: getBaiduSearchResultsBySimpleRule 
	 * @Description: TODO(抓取百度搜索结果) 
	 * @param @param rule
	 * @param @return 
	 * @return List<BaseResult> 
	 * @throws 
	 */ 
	public List<BaseResult> getBaiduSearchResultsBySimpleRule(SimpleUrlRule rule){
		List<BaseResult> results = new ArrayList<BaseResult>();
		String separator = "\r\n";
		
		AbstractElement abstractElement = new AbstractElement() {
			@Override
			public String getTargetUrl(String prependUrl, Element target) {
				return null;
			}
		};
		Elements el = abstractElement.createElements(rule);
		if(!CommonUtils.isEmptyList(el)){
			for (Element e : el) {
				Elements titleElement = e.getElementsByTag("h3");
				if(!CommonUtils.isEmptyList(titleElement)){
					for (Element title : titleElement) {
						BaseResult result = new BaseResult();
						result.setTitle(title.text()+"\r\n");
						List<String> hyperlinks = new ArrayList<String>();
						
						if(title.select("a")!=null && !title.select("a").isEmpty()){
							String link = title.select("a").get(0).attr("href");
							hyperlinks.add(link);
						}
						result.setHref(hyperlinks);
						
						StringBuffer contentText = new StringBuffer();
						StringBuffer contentHtml = new StringBuffer();
						Elements contentElement = title.siblingElements();
						if(!CommonUtils.isEmptyList(contentElement)){
							for (Element subE : contentElement) {
								contentText.append(subE.text());
								contentText.append(separator);
								contentHtml.append(subE.html());
								contentHtml.append(separator);
							}
						}
						result.setText(contentText.toString());
						result.setHtml(contentHtml.toString());
						
						results.add(result);
					}
				}
			}
		}
		
		return results;
	}
}
