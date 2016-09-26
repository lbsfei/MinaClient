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
 * Purpose:客户端
 * Create Time: 2014-8-19 下午4:41:55
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
	 * 这个方法，通过keystore和truststore文件返回一个SSLContext对象
	 * 
	 * @return
	 */
	public SSLContext getSslContext() {
		SSLContext sslContext = null;
		try {
			/*
			 * 提供keystore的存放目录，读取keystore的文件内容
			 */
			File keyStoreFile = new File("C:/Users/Myp/keystore.jks");

			/*
			 * 提供truststore的存放目录，读取truststore的文件内容
			 */
			File trustStoreFile = new File(
					"C:/Users/Myp/truststore.jks");

			if (keyStoreFile.exists() && trustStoreFile.exists()) {
				final KeyStoreFactory keyStoreFactory = new KeyStoreFactory();
				System.out.println("Url is: " + keyStoreFile.getAbsolutePath());
				keyStoreFactory.setDataFile(keyStoreFile);

				/*
				 * 这个是当初我们使用keytool创建keystore和truststore文件的密码,也是上次让你们一定要记住密码的原因了
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