package com.example.mina.ssl;

import java.io.File;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.mina.filter.ssl.KeyStoreFactory;
import org.apache.mina.filter.ssl.SslContextFactory;

/**
 * <pre>
 * Project Name:SSLContextGenerator
 * Package:com.example.mina.ssl
 * FileName:SSLContextGenerator.java
 * Purpose:�ͻ���
 * Create Time: 2014-8-19 ����4:41:55
 * Create Specification:
 * Modified Time:
 * Modified by:
 * Modified Specification:
 * Version: 1.0
 * </pre>
 * 
 * @author myp
 */
public class SSLContextGenerator {

	/**
	 * ���������ͨ��keystore��truststore�ļ�����һ��SSLContext����
	 * 
	 * @return
	 */
	public SSLContext getSslContext() {
		SSLContext sslContext = null;
		try {
			/*
			 * �ṩkeystore�Ĵ��Ŀ¼����ȡkeystore���ļ�����
			 */
			File keyStoreFile = new File("C:/Users/Myp/keystore.jks");

			/*
			 * �ṩtruststore�Ĵ��Ŀ¼����ȡtruststore���ļ�����
			 */
			File trustStoreFile = new File(
					"C:/Users/Myp/truststore.jks");

			if (keyStoreFile.exists() && trustStoreFile.exists()) {
				final KeyStoreFactory keyStoreFactory = new KeyStoreFactory();
				System.out.println("Url is: " + keyStoreFile.getAbsolutePath());
				keyStoreFactory.setDataFile(keyStoreFile);

				/*
				 * ����ǵ�������ʹ��keytool����keystore��truststore�ļ�������,Ҳ���ϴ�������һ��Ҫ��ס�����ԭ����
				 */
				keyStoreFactory.setPassword("123456");

				final KeyStoreFactory trustStoreFactory = new KeyStoreFactory();
				trustStoreFactory.setDataFile(trustStoreFile);
				trustStoreFactory.setPassword("123456");

				final SslContextFactory sslContextFactory = new SslContextFactory();
				final KeyStore keyStore = keyStoreFactory.newInstance();
				sslContextFactory.setKeyManagerFactoryKeyStore(keyStore);

				final KeyStore trustStore = trustStoreFactory.newInstance();
				sslContextFactory.setTrustManagerFactoryKeyStore(trustStore);
				sslContextFactory
						.setKeyManagerFactoryKeyStorePassword("134426myp");
				sslContext = sslContextFactory.newInstance();
				System.out.println("SSL provider is: "
						+ sslContext.getProvider());
			} else {
				System.out
						.println("Keystore or Truststore file does not exist");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return sslContext;
	}
}