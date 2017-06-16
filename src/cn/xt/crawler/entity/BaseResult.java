package cn.xt.crawler.entity;

import java.util.List;

public class BaseResult {
	private Integer id;
	private String title;
	private String text;
	private String html;
	private List<String> href;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public List<String> getHref() {
		return href;
	}
	public void setHref(List<String> href) {
		this.href = href;
	}
}
