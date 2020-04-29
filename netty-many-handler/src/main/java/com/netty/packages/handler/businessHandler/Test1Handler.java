package com.netty.packages.handler.businessHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.netty.packages.annotation.HandlerRegister;
import com.netty.packages.entity.businessEntity.Test1Entity;

@HandlerRegister
public class Test1Handler extends SimpleChannelInboundHandler<Test1Entity> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Test1Entity msg)
			throws Exception {
		System.out.println("*******Test1Handler");
	}

}
