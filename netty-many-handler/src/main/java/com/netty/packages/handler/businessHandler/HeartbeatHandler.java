package com.netty.packages.handler.businessHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.netty.packages.annotation.HandlerRegister;
import com.netty.packages.entity.businessEntity.HeartbeatEntity;

@HandlerRegister
public class HeartbeatHandler extends SimpleChannelInboundHandler<HeartbeatEntity>{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HeartbeatEntity msg)
			throws Exception {
		System.out.println("*******HeartbeatHandler");
		
	}

}
