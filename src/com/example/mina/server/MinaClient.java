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
 * Purpose:�ͻ���
 * Create Time: 2014-8-19 ����4:36:55
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
		 * 1.����һ��socket����,���ӵ�������
		 */
		connector = new NioSocketConnector();

		/*
		 * ��ȡ��������,������ӹ�����
		 */
		DefaultIoFilterChainBuilder chain = connector.getFilterChain();

		/*
		 * 2.Ϊ������ӹ�������SSL����־�����������
		 */
		// SSLContextGenerator�������Լ�д��һ��SSL�����Ĳ�����,�Ժ�ὲ��
		//SslFilter sslFilter = new SslFilter(
				//new SSLContextGenerator().getSslContext());
		// ����Ϊ�ͻ���ģʽ
		//sslFilter.setUseClientMode(true);
		// a.ssl�����������һ��Ҫ��һ����ӣ��������ݲ�����м���
		//chain.addFirst("sslFilter", sslFilter);

		// b.�����־������
		chain.addLast("logger", new LoggingFilter());
		
		
		

		// c.����ַ��ı��������
		chain.addLast("codec", new ProtocolCodecFilter(new CharsetFactory()));

		/*
		 * 3.������Ϣ�����������ڴ�����յ�����Ϣ
		 */
		connector.setHandler(new MsgHanler());

		/*
		 * 4.����IP�Ͷ˿ں����ӵ�������
		 */
		future = connector.connect(new InetSocketAddress("192.168.1.12", 3456));
		// �ȴ����Ӵ������
		future.awaitUninterruptibly();

		/*
		 * 5.��ȡsession����,ͨ��session�����������������Ϣ��
		 */
		session = future.getSession();
		session.getConfig().setUseReadOperation(true);
		return future.isConnected();
	}

	/**
	 * ��������������Ϣ
	 * 
	 * @param message
	 */
	public void sendMsg2Server(String message) {
		session.write(message);
	}

	/**
	 * �ر��������������
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