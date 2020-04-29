package com.netty.packages.config;


public class SystemConfig {
	
	private int port = 8080;
	
	private String host = "127.0.0.1";
	
	private int readerIdleTimeSeconds = 60 ;
	
	private int writerIdleTimeSeconds = 60 ;
	
	private int allIdleTimeSeconds = 60 ;
	
	public int getReaderIdleTimeSeconds() {
		return readerIdleTimeSeconds;
	}

	public int getWriterIdleTimeSeconds() {
		return writerIdleTimeSeconds;
	}

	public int getAllIdleTimeSeconds() {
		return allIdleTimeSeconds;
	}

	public int getPort() {
		return port;
	}

	public String getHost() {
		return host;
	}
	
	
}
