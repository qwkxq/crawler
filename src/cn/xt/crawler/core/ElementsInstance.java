package cn.xt.crawler.core;

import java.io.IOException;
import java.util.Arrays;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.xt.crawler.rule.SimpleRule;
import cn.xt.crawler.rule.SimpleUrlRule;

public class ElementsInstance {

	private ElementsInstance() {
	}

	private static final class Instance {
		static final ElementsInstance instance = new ElementsInstance();
	}

	public static ElementsInstance getInstance() {
		return Instance.instance;
	}

	public Elements createElements(SimpleUrlRule rule) {
		Connection conn = Jsoup.connect(rule.getUrl());
		if (rule.getData() != null) {
			conn.data(rule.getData());
		}
		Document doc = null;
		try {
			doc = (rule.getMethod() == SimpleUrlRule.GET ? conn.get() : conn
					.post());
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
}
