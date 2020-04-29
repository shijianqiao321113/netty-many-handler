package com.netty.packages.handler.businessHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import com.netty.packages.annotation.HandlerRegister;
import com.netty.packages.entity.BasicsEntity;
import com.netty.packages.utils.StringManager;

@HandlerRegister(loadingOrder = StringManager._1)
public class CheckLoginHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		BasicsEntity b = (BasicsEntity)msg;
		System.out.println("=========CheckLoginHandler======="+b.getBusinessType());
		ctx.fireChannelRead(msg);
	}

}
