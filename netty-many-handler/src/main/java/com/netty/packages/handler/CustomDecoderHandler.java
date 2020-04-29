package com.netty.packages.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.lang.reflect.Constructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netty.packages.Application;
import com.netty.packages.annotation.HandlerRegister;
import com.netty.packages.utils.StringManager;

@HandlerRegister(loadingOrder = Byte.MIN_VALUE)
public class CustomDecoderHandler extends LengthFieldBasedFrameDecoder {

	private static final Logger logger = LoggerFactory.getLogger(CustomDecoderHandler.class);
	
	public CustomDecoderHandler() {
		super(Integer.MAX_VALUE,StringManager._2,StringManager._4,StringManager._8,StringManager._0);
	}

	/*
	 * +----------------------------------------------------------------------------+     
	 * |     	1	   |     	1		|     	4		  |     8	   	  |   ?  	|
	 * |  messageType  |  businessType  |  bodyLength  	  | messageNumber |  body   |
	 * +----------------------------------------------------------------------------+ 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		if(in.readableBytes() < StringManager._14){
			return null;
		}
		byte messageType = in.readByte();
		if(messageType != (byte)StringManager._1 && 
				messageType != (byte)StringManager._2){
			return null;
		}
		byte businessType = in.readByte();
		Class BusinessEntityClass = Application.getBusinessEntityClassByKey(businessType);
		if(BusinessEntityClass == null){
			return null;
		}
		int bodyLength = in.readInt();
		long messageNumber = in.readLong();
		if(in.readableBytes() < bodyLength){
			return null;
		}
		byte[] body = new byte[bodyLength];
		in.readBytes(body);
		
		Class cls = Class.forName(BusinessEntityClass.getName());
		Constructor con = cls.getConstructor(byte.class, byte.class,long.class,int.class, byte[].class);
		Object obj = con.newInstance(messageType, businessType, messageNumber, bodyLength, body);
		logger.info(" message decoder read : " + obj.toString());
		return obj;
	}

}
