package com.googlecode.httpliar.handler.block;

import java.nio.charset.Charset;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * HTML��
 * @author luanjia@taobao.com
 *
 */
public class HtmlBlock extends TextBlock {

	/*
	 * html�ĵ�
	 */
	private final Document document;
	
	public HtmlBlock(String text, Charset charset) {
		super(text, charset);
		this.document = Jsoup.parse(text);
	}

	/**
	 * ��ȡ��ǰhtml�ĵ�
	 * @return
	 */
	public Document getDocument() {
		return document.clone();
	}

}
