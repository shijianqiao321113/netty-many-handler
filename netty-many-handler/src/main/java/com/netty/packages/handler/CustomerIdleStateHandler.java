package com.netty.packages.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateHandler;

import com.netty.packages.Application;
import com.netty.packages.annotation.HandlerRegister;

@HandlerRegister(loadingOrder = Byte.MIN_VALUE)
public class CustomerIdleStateHandler extends IdleStateHandler {

	public CustomerIdleStateHandler(){
		super(Application.getSystemConfig().getReaderIdleTimeSeconds(),Application.getSystemConfig().getWriterIdleTimeSeconds(),Application.getSystemConfig().getAllIdleTimeSeconds());
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
		super.userEventTriggered(ctx, evt);
	}
	

}
