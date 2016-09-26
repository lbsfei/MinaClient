package com.example.mina.hanlder;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * Project Name:MsgHanler
 * Package:com.example.mina.handler
 * FileName:MsgHanler.java
 * Purpose:I/O消息处理器,从这里我们就可以看出Mina是事件驱动的
 * Create Time: 2014-8-19 下午4:39:55
 * Create Specification:
 * Modified Time:
 * Modified by:
 * Modified Specification:
 * Version: 1.0
 * </pre>
 * 
 * @author myp
 */
public class MsgHanler extends IoHandlerAdapter {
	private static final Logger log = LoggerFactory.getLogger(MsgHanler.class);

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		// 出现异常
		log.error("--------exception--------");
		super.exceptionCaught(session, cause);
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		// 从服务器中接收到消息后的处理
		log.info("--------msg receive--------");
		log.info("Message:{}" + message.toString());
		super.messageReceived(session, message);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		// 往服务器中发送消息
		log.info("--------msg sent--------");
		super.messageSent(session, message);
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// 当session被创建的时候调用
		log.info("--------session create--------");
		super.sessionCreated(session);
	}
}
