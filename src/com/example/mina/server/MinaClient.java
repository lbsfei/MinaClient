package com.example.mina.server;

import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.filter.ssl.SslFilter;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.example.mina.charset.CharsetFactory;
import com.example.mina.hanlder.MsgHanler;
import com.example.mina.ssl.SSLContextGenerator;

/**
 * <pre>
 * Project Name:MinaClient
 * Package:com.example.mina.server
 * FileName:MinaClient.java
 * Purpose:客户端
 * Create Time: 2014-8-19 下午4:36:55
 * Create Specification:
 * Modified Time:
 * Modified by:
 * Modified Specification:
 * Version: 1.0
 * </pre>
 * 
 * @author myp
 */
public class MinaClient {

	private SocketConnector connector;
	private ConnectFuture future;
	private IoSession session;

	public boolean connect() {
		/*
		 * 1.创建一个socket连接,连接到服务器
		 */
		connector = new NioSocketConnector();

		/*
		 * 获取过滤器链,用于添加过滤器
		 */
		DefaultIoFilterChainBuilder chain = connector.getFilterChain();

		/*
		 * 2.为连接添加过滤器，SSL、日志、编码过滤器
		 */
		// SSLContextGenerator是我们自己写的一个SSL上下文产生器,稍后会讲到
		//SslFilter sslFilter = new SslFilter(
				//new SSLContextGenerator().getSslContext());
		// 设置为客户端模式
		//sslFilter.setUseClientMode(true);
		// a.ssl过滤器，这个一定要第一个添加，否则数据不会进行加密
		//chain.addFirst("sslFilter", sslFilter);

		// b.添加日志过滤器
		chain.addLast("logger", new LoggingFilter());
		
		
		

		// c.添加字符的编码过滤器
		chain.addLast("codec", new ProtocolCodecFilter(new CharsetFactory()));

		/*
		 * 3.设置消息处理器，用于处理接收到的消息
		 */
		connector.setHandler(new MsgHanler());

		/*
		 * 4.根据IP和端口号连接到服务器
		 */
		future = connector.connect(new InetSocketAddress("192.168.1.12", 3456));
		// 等待连接创建完成
		future.awaitUninterruptibly();

		/*
		 * 5.获取session对象,通过session可以向服务器发送消息；
		 */
		session = future.getSession();
		session.getConfig().setUseReadOperation(true);
		return future.isConnected();
	}

	/**
	 * 往服务器发送消息
	 * 
	 * @param message
	 */
	public void sendMsg2Server(String message) {
		session.write(message);
	}

	/**
	 * 关闭与服务器的连接
	 * 
	 * @return
	 */
	public boolean close() {
		CloseFuture future = session.getCloseFuture();
		future.awaitUninterruptibly(1000);
		connector.dispose();
		return true;
	}
}