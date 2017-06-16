package cn.xt.crawler.core;

import java.io.IOException;
import java.util.Arrays;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.xt.crawler.rule.SimpleUrlRule;

public abstract class AbstractElement {
	/** 
	 * @Title: createElements 
	 * @Description: TODO(根据url规则实例生成对应页面对象(Elements)) 
	 * @param @param rule
	 * @param @return 
	 * @return Elements 
	 * @throws 
	 */ 
	public Elements createElements(SimpleUrlRule rule) {
		Connection conn = Jsoup.connect(rule.getUrl());
		
		if (rule.getData() != null) {
			conn.data(rule.getData());
		}
		Document doc = null;
		try {
			conn.timeout(5000);
			//conn.userAgent("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0");
			//conn.header("Accept", "	text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			
			doc = (rule.getMethod() == SimpleUrlRule.GET ? conn.get() : conn.post());
			if (rule.getQueryType() == SimpleUrlRule.TAG) {
				return doc.getElementsByTag(rule.getQuery());
			} else if (rule.getQueryType() == SimpleUrlRule.CLASS) {
				return doc.getElementsByClass(rule.getQuery());
			} else if (rule.getQueryType() == SimpleUrlRule.ID) {
				return new Elements(Arrays.asList(new Element[] { doc
						.getElementById(rule.getQuery()) }));
			} else if (rule.getQueryType() == SimpleUrlRule.SELECTOR) {
				return doc.select(rule.getQuery());
			} else {

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/** 
	 * @Title: setTargetElemets2ResultsBySelectors 
	 * @Description: TODO(
	 * 通过selectors递归寻找节点，直到最后找到所有节点，添加到集合里,
	 * 目前只提供了统一的获取页面地址的手段，即每次递归的页面都从子类覆盖的getTargetUrl中提取
	 * ) 
	 * @param @param index 选择器集合索引，当其等于集合长度-1时，将结果集添加到results，否则继续递归
	 * @param @param selectors 选择器集合，递归查找节点的依据
	 * @param @param rootUrl 根节点url，目前作用主要是给没有主机前缀的地址添加前缀
	 * @param @param results 符合条件的Elements将被添加给它
	 * @param @param rule  规则，用于生成某个url对应的页面对象(Elements)
	 * @return void 
	 * @throws 
	 */ 
	public void setTargetElemets2ResultsBySelectors(final int index,
			final String[] selectors, final String rootUrl, final Elements results,
			SimpleUrlRule rule) {

		final Elements roots = this.createElements(rule);
		
		String select = selectors[index];
		
		final Elements targets = roots.get(0).select(select);
		
		if (index >= selectors.length - 1) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					dispatcher(roots.get(0),targets);
				}
			}).start();
			results.addAll(targets);
			return;
		}

		for (final Element target : targets) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					String targetUrl = getTargetUrl(rootUrl, target);
					//System.out.println(targetUrl);
					SimpleUrlRule targetRule = new SimpleUrlRule(targetUrl, "html",
							SimpleUrlRule.TAG, SimpleUrlRule.GET);
					int tempIndex  = index;
					setTargetElemets2ResultsBySelectors(++tempIndex, selectors, rootUrl,
							results, targetRule);
				}
			}).start();
		}
	}
	
	/** 
	 * @Title: dispatcher 
	 * @Description: TODO(获得符合条件的Elements做的事情) 
	 * @param @param root
	 * @param @param targets 
	 * @return void 
	 * @throws 
	 */ 
	public void dispatcher(Element root,Elements targets) {
	}
	
	/** 
	 * @Title: getTargetUrl 
	 * @Description: TODO(指定所有规则需要的url的生成策略) 
	 * @param @param prependUrl
	 * @param @param target
	 * @param @return 
	 * @return String 
	 * @throws 
	 */ 
	public abstract String getTargetUrl(String prependUrl, Element target);
}
