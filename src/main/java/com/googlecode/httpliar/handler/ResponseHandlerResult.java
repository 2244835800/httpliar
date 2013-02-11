package com.googlecode.httpliar.handler;

import com.googlecode.httpliar.handler.block.DataBlock;


/**
 * Ӧ������
 * @author luanjia@taobao.com
 *
 */
public class ResponseHandlerResult extends HandlerResult {

	private final DataBlock block;
	
	public ResponseHandlerResult(final DataBlock block) {
		this.block = block;
	}

	/**
	 * ��ȡ���ݿ�
	 * @return
	 */
	public DataBlock getBlock() {
		return block;
	}

}
