package com.googlecode.httpliar;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.collections.ListUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ConnectHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.httpliar.handler.HttpRequestHandler;
import com.googlecode.httpliar.handler.HttpResponseHandler;
import com.googlecode.httpliar.handler.TextHttpResponseHandler;
import com.googlecode.httpliar.handler.UnCompressHttpResponseHandler;

/**
 * HttpLiar������
 * @author luanjia@taobao.com
 *
 */
public class HttpLiarServer {

	private static final Logger logger = LoggerFactory.getLogger("httpliar");
	
	private final int port;			//����˿�
	private final Server server;	//���������
	private final ArrayList<HttpRequestHandler> httpRequestHandlers = new ArrayList<HttpRequestHandler>();
	private final ArrayList<HttpResponseHandler> httpResponseHandlers = new ArrayList<HttpResponseHandler>();
	
	@SuppressWarnings("unchecked")
	public HttpLiarServer(final int port) {
		this(port, ListUtils.EMPTY_LIST, ListUtils.EMPTY_LIST);
	}
	
	/**
	 * ֧��handler�Ĺ��캯��
	 * @param port
	 * @param httpRequestHandlers
	 * @param httpResponseHandlers
	 */
	public HttpLiarServer(final int port, 
			final List<HttpRequestHandler> httpRequestHandlers, 
			final List<HttpResponseHandler> httpResponseHandlers) {
		this.port = port;
		server = new Server();
		this.httpRequestHandlers.addAll(httpRequestHandlers);
		this.httpResponseHandlers.addAll(httpResponseHandlers);
	}
	
	/**
	 * ����������
	 * @throws Exception
	 */
	public void startProxy() throws Exception {
		
		SelectChannelConnector connector = new SelectChannelConnector();
        connector.setPort(port);
        server.addConnector(connector);

        HandlerCollection handlers = new HandlerCollection();
        server.setHandler(handlers);

        
        // Setup proxy servlet
        ServletContextHandler context = new ServletContextHandler(handlers, "/", ServletContextHandler.SESSIONS);
        ServletHolder proxyServlet = new ServletHolder(HttpLiarProxyServlet.class){

			@Override
			protected Servlet newInstance() throws ServletException,
					IllegalAccessException, InstantiationException {
				final Servlet servlet = super.newInstance();
				if( HttpLiarProxyServlet.class.isAssignableFrom(this._class)  ) {
					final HttpLiarProxyServlet httpLiarProxyServlet = (HttpLiarProxyServlet)servlet;
					
					// inject requestHandlers
					injectHttpRequestHandlers(httpLiarProxyServlet.getHttpRequestHandlers());
					
					// inject responseHandlers
					injectHttpResponseHandlers(httpLiarProxyServlet.getHttpResponseHandlers());
					
				}
				return servlet;
			}
        	
        };
        context.addServlet(proxyServlet, "/*");
        
        // Setup proxy handler to handle CONNECT methods
        handlers.addHandler(new ConnectHandler());
        server.start();
		
		logger.info("httpliar[port={}] started!", port);
	}
	
	/**
	 * �رշ�����
	 * @throws Exception
	 */
	public void stopProxy() throws Exception {
		if( null != server ) {
			server.stop();
			server.destroy();
		}
		logger.info("httpliar[port={}] stoped!", port);
	}
	
	/**
	 * ��ʼHttpRequestHandler������
	 * @param handlers
	 */
	private void injectHttpRequestHandlers(List<HttpRequestHandler> handlers) {
		handlers.addAll(httpRequestHandlers);
	}
	
	/**
	 * ��ʼ��HttpResponseHandler������
	 * @param handlers
	 */
	private void injectHttpResponseHandlers(List<HttpResponseHandler> handlers) {
		handlers.add(new UnCompressHttpResponseHandler());
		handlers.add(new TextHttpResponseHandler());
		handlers.addAll(httpResponseHandlers);
	}
	
}
