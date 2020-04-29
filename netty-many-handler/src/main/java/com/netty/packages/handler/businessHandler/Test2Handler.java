package com.netty.packages.handler.businessHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.netty.packages.annotation.HandlerRegister;
import com.netty.packages.entity.businessEntity.Test2Entity;

@HandlerRegister
public class Test2Handler extends SimpleChannelInboundHandler<Test2Entity> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Test2Entity msg)
			throws Exception {
		System.out.println("*******Test2Handler");
		
	}

}
