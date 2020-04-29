package com.netty.packages.entity;

import java.util.Arrays;

public class BasicsEntity {
	
	/*包类型，心跳包，业务包 1位	1心跳包，2业务包*/
	public byte messageType;
	
	/*业务类型代码 1位*/
	public byte businessType;
	
	/*内容长度 4位*/
	public int bodyLength;

	/*唯一序列号 8位*/
	public long messageNumber;
	
	/*内容*/
	public byte[] body;
	
	public BasicsEntity(byte messageType, byte businessType,
			long messageNumber, int bodyLength, byte[] body) {
		super();
		this.messageType = messageType;
		this.businessType = businessType;
		this.messageNumber = messageNumber;
		this.bodyLength = bodyLength;
		this.body = body;
	}

	public byte getMessageType() {
		return messageType;
	}

	public byte getBusinessType() {
		return businessType;
	}

	public long getMessageNumber() {
		return messageNumber;
	}

	public int getBodyLength() {
		return bodyLength;
	}

	public byte[] getBody() {
		return body;
	}
	
	@Override
	public String toString() {
		return "BasicsEntity [messageType=" + messageType + ", businessType="
				+ businessType + ", bodyLength=" + bodyLength
				+ ", messageNumber=" + messageNumber + ", body="
				+ Arrays.toString(body) + "]";
	}
}
