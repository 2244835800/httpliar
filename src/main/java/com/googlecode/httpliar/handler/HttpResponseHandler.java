package com.googlecode.httpliar.handler;

import com.googlecode.httpliar.HttpLiarExchange;
import com.googlecode.httpliar.handler.block.DataBlock;

/**
 * HTTPӦ����
 * @author luanjia@taobao.com
 *
 */
public interface HttpResponseHandler {

	/**
	 * �ж��Ƿ��ܴ���HTTPӦ��
	 * @param exchange
	 * @return
	 */
	boolean isHandleResponse(final HttpLiarExchange exchange);
	
	/**
	 * ����HTTPӦ��
	 * @param exchange
	 * @param block
	 * @return
	 * @throws Exception
	 */
	ResponseHandlerResult handleResponse(
			final HttpLiarExchange exchange,
			final DataBlock block) throws Exception;
	
}
