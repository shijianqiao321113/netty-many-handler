package com.netty.packages.entity.businessEntity;

import com.netty.packages.annotation.EntityRegister;
import com.netty.packages.entity.BasicsEntity;
import com.netty.packages.utils.StringManager;

@EntityRegister(businessType = StringManager._4)
public class Test2Entity extends BasicsEntity {

	public Test2Entity(byte messageType, byte businessType, long messageNumber,
			int bodyLength, byte[] body) {
		super(messageType, businessType, messageNumber, bodyLength, body);
		System.out.println("========Test2Entity");
	}

}
