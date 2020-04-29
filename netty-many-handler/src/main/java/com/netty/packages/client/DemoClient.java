package com.netty.packages.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author liupengr
 * @date 2020/2/16 16:10
 */
public class DemoClient {

    private String ip;
    private int port;
    private int workerThreads; // 用于业务处理的计算线程

    public DemoClient(String ip, int port, int workerThreads) {
        this.ip = ip;
        this.port = port;
        this.workerThreads = workerThreads;
    }

    public void start() {
        EventLoopGroup workerGroup = new NioEventLoopGroup(workerThreads);
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline p = ch.pipeline();
                    /*p.addLast(new StringDecoder());
                    p.addLast(new StringEncoder());*/
                    /*p.addLast(new DemoClientHandler());*/
                    p.addLast(new ClientEncode());
                }
            });
            bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                    .option(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = bootstrap.connect(ip, port).sync();
            
            
            future.channel().writeAndFlush("Hello Netty Server ,I am a common client");
            Thread.sleep(2000);
            future.channel().writeAndFlush("Hello Netty Server ,I am a common client");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            workerGroup.shutdownGracefully();
        }

    }
    public static void main(String[] args) {
        DemoClient client=new DemoClient("127.0.0.1",9999,1);
        client.start();
    }
}