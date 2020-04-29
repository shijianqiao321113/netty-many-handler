package com.netty.packages.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netty.packages.annotation.HandlerRegister;
import com.netty.packages.entity.BasicsEntity;
import com.netty.packages.utils.StringManager;

@HandlerRegister
public class CustomEncoderHandler extends MessageToByteEncoder<BasicsEntity> {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomEncoderHandler.class);

	/*
	 * +----------------------------------------------------------------------------+     
	 * |     	1	   |     	1		|     	4		  |     8	   	  |   ?  	|
	 * |  messageType  |  businessType  |  bodyLength  	  | messageNumber |  body   |
	 * +----------------------------------------------------------------------------+ 
	 */
	@Override
	protected void encode(ChannelHandlerContext ctx, BasicsEntity msg,
			ByteBuf out) throws Exception {
		if(msg == null){
			return ;
		}
		out.writeByte(msg.getMessageType());
		out.writeByte(msg.getBusinessType());
		out.writeInt(msg.getBody().length);
		out.writeLong(msg.getMessageNumber());
		if(msg.getBody().length > StringManager._0){
			out.writeBytes(msg.getBody());
		}
		logger.info(" message encoder write : " + msg.toString());
		return ;
	}

}
