package com.netty.packages.handler.businessHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.netty.packages.annotation.HandlerRegister;
import com.netty.packages.entity.businessEntity.LoginEntity;
import com.netty.packages.utils.StringManager;

@HandlerRegister(loadingOrder = StringManager._0)
public class LoginHandler extends SimpleChannelInboundHandler<LoginEntity> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, LoginEntity msg)
			throws Exception {
		System.out.println("*******LoginHandler");
		
	}

}
