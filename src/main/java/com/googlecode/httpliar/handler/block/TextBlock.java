package com.googlecode.httpliar.handler.block;

import java.nio.charset.Charset;

/**
 * �ı���
 * @author luanjia@taobao.com
 *
 */
public class TextBlock extends DataBlock {

	// �ַ���
	private final Charset charset;
	private final String text;
	
	public TextBlock(byte[] datas, Charset charset) {
		super(datas);
		this.charset = charset;
		this.text = new String(datas, charset);
	}
	
	public TextBlock(String text, Charset charset) {
		this(text.getBytes(charset), charset);
	}

	/**
	 * ��ȡ�ı����е��ı�����
	 * @return
	 */
	public String getText() {
		return this.text;
	}
	
	/**
	 * ��ȡ�ı����е��ַ���
	 * @return
	 */
	public Charset getCharset() {
		return charset;
	}
	
}
