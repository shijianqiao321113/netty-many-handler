package com.netty.packages.entity.businessEntity;

import com.netty.packages.annotation.EntityRegister;
import com.netty.packages.entity.BasicsEntity;
import com.netty.packages.utils.StringManager;

@EntityRegister(businessType = StringManager._2)
public class LoginEntity extends BasicsEntity {

	public LoginEntity(byte messageType, byte businessType, long messageNumber,
			int bodyLength, byte[] body) {
		super(messageType, businessType, messageNumber, bodyLength, body);
	}

}
