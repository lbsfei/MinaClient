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
 * Purpose:I/O��Ϣ������,���������ǾͿ��Կ���Mina���¼�������
 * Create Time: 2014-8-19 ����4:39:55
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
		// �����쳣
		log.error("--------exception--------");
		super.exceptionCaught(session, cause);
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		// �ӷ������н��յ���Ϣ��Ĵ���
		log.info("--------msg receive--------");
		log.info("Message:{}" + message.toString());
		super.messageReceived(session, message);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		// ���������з�����Ϣ
		log.info("--------msg sent--------");
		super.messageSent(session, message);
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// ��session��������ʱ�����
		log.info("--------session create--------");
		super.sessionCreated(session);
	}
}
