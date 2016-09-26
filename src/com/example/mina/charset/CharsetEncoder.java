package com.example.mina.charset;

import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.textline.LineDelimiter;

public class CharsetEncoder implements ProtocolEncoder {
	private static final Logger log = Logger.getLogger(CharsetEncoder.class);
	private final static Charset charset = Charset.forName("UTF-8");

	@Override
	public void dispose(IoSession arg0) throws Exception {
		log.info("----------dipose--------");
	}

	@Override
	public void encode(IoSession arg0, Object arg1, ProtocolEncoderOutput arg2)
			throws Exception {
		log.info("----------encode--------");
		IoBuffer buff = IoBuffer.allocate(100).setAutoExpand(true);
		buff.putString(arg1.toString(), charset.newEncoder());
		// put 当前系统默认换行符
		buff.putString(LineDelimiter.DEFAULT.getValue(), charset.newEncoder());
		// 为下一次读取数据做准备
		buff.flip();
		arg2.write(arg1);
	}
}
