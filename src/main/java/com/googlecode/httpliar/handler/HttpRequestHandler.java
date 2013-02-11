package com.googlecode.httpliar.handler;

import com.googlecode.httpliar.HttpLiarExchange;

/**
 * ��������
 * @author luanjia@taobao.com
 *
 */
public interface HttpRequestHandler {

	/**
	 * �ж��Ƿ��ܴ���HTTPӦ��
	 * @param exchange
	 * @return
	 */
	boolean isHandleRequest(final HttpLiarExchange exchange);
	
	/**
	 * �������������������
	 * @param exchange
	 * @return
	 * @throws Exception
	 */
	RequestHandlerResult handleRequest(final HttpLiarExchange exchange) throws Exception;
	
}
