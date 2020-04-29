package com.netty.packages.client;

import java.util.Arrays;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ClientEncode extends MessageToByteEncoder<Object> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out)
			throws Exception {
		
		out.writeByte((byte)2);
		out.writeByte((byte)3);
		/*out.writeInt(11);*/
		
		out.writeByte((byte)0);
		out.writeByte((byte)0);
		out.writeByte((byte)0);
		out.writeByte((byte)11);
		
		out.writeByte((byte)0);
		out.writeByte((byte)0);
		out.writeByte((byte)0);
		out.writeByte((byte)0);
		out.writeByte((byte)0);
		out.writeByte((byte)0);
		out.writeByte((byte)0);
		out.writeByte((byte)0);
		
		/*out.writeLong((long)(Math.random()*100000000));*/
		out.writeBytes(new byte[]{1,2,3,4,5,6,7,8,9,1,7});
	}

	
	public static void main(String[] args) {
		System.out.println(Arrays.toString(toBytes(0)));
		System.out.println(Arrays.toString(int2ByteArray(0)));
		
		System.out.println(Arrays.toString(toBytes(0l)));
	}
	
	public static byte[] toBytes(int number){
        byte[] bytes = new byte[4];
        bytes[0] = (byte)number;
        bytes[1] = (byte) (number >> 8);
        bytes[2] = (byte) (number >> 16);
        bytes[3] = (byte) (number >> 24);
        return bytes;
    }
	
	public static byte[] toBytes(long val) {
        byte [] b = new byte[8];
        for (int i = 7; i > 0; i--) {
          //强制转型，后留下长整形的低8位
          b[i] = (byte) val;
          String str = Long.toBinaryString( val) ;
          String lb = Long.toBinaryString( b[i] ) ;
          String lb2 = Long.toBinaryString( b[i]&0xff ) ;
          //向右移动8位，则第二次循环则计算第二个8位数
          val >>>= 8;
        }
        b[0] = (byte) val;
        return b;
      }
	
	
	public static byte[] int2ByteArray(int i){
		byte[] result=new byte[4];
		         result[0]=(byte)((i >> 24)& 0xFF);
		         result[1]=(byte)((i >> 16)& 0xFF);
		         result[2]=(byte)((i >> 8)& 0xFF);
		         result[3]=(byte)(i & 0xFF);
		         return result;
		     }
}