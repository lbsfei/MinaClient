package com.example.mina.charset;

import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class CharsetDecoder implements ProtocolDecoder {
	private static final Logger log = Logger.getLogger(CharsetDecoder.class);
	private static final Charset charset = Charset.forName("UTF-8");
	private IoBuffer buffer = IoBuffer.allocate(100).setAutoExpand(true);

	@Override
	public void decode(IoSession arg0, IoBuffer arg1, ProtocolDecoderOutput arg2)
			throws Exception {
		log.info("----------decode--------");

		while (arg1.hasRemaining()) {
			byte b = arg1.get();
			if (b == '\n') {
				buffer.flip();
				byte[] bytes = new byte[buffer.limit()];
				buffer.get(bytes);
				String msg = new String(bytes, charset);
				arg2.write(msg);
				buffer = IoBuffer.allocate(100).setAutoExpand(true);
				log.info("Message:" + msg);
			} else {
				buffer.put(b);
			}
		}
	}

	@Override
	public void dispose(IoSession arg0) throws Exception {
		log.info("----------dipose--------");
		log.info((String) arg0.getCurrentWriteMessage());
	}

	@Override
	public void finishDecode(IoSession arg0, ProtocolDecoderOutput arg1)
			throws Exception {
		log.info("----------finish decode--------");
	}
}
