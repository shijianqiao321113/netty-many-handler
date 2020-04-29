package com.netty.packages;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netty.packages.annotation.EntityRegister;
import com.netty.packages.annotation.HandlerRegister;
import com.netty.packages.config.SystemConfig;
import com.netty.packages.utils.StringManager;

public class Application {
	
	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	
	private static final EventLoopGroup bossGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());
	
	private static final EventLoopGroup workerGroup = new NioEventLoopGroup();
	
	private static final Map<String,String> execParameterMap = new HashMap<String,String>();
	
	private static final SystemConfig systemConfig = new SystemConfig();
	
	private static final List<Class<?>> handlerList = new ArrayList<Class<?>>();
	
	@SuppressWarnings("rawtypes")
	private static final Map<Byte,Class> businessEntityClassMap = new HashMap<Byte,Class>();
	
	/*报文匹配实体对象映射初始化*/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void messageToEntityInit(){
		List<Class<?>> list = StringManager.getClasses(Application.class.getPackage().getName());
		for(Class c : list){
			if(c.isAnnotationPresent(EntityRegister.class)){
				businessEntityClassMap.put(((EntityRegister)c.getAnnotation(EntityRegister.class)).businessType(), c);
				logger.info(" register entity businessType : " + ((EntityRegister)c.getAnnotation(EntityRegister.class)).businessType() + " ===>> " +c.getName());
			}
		}
	}
	
	/*根据业务类型代码，获取对应的业务类*/
	@SuppressWarnings("rawtypes")
	public static Class getBusinessEntityClassByKey(byte key){
		return businessEntityClassMap.get(key);
	}
	
	/*提供输入参数全局获取方法*/
	public static String getExecParameterValueByKey(String key){
		return execParameterMap.get(key);
	}
	
	/*提供全局配置类方法*/
	public static SystemConfig getSystemConfig(){
		return systemConfig;
	}
	
	/*输入参数转对象映射*/
	private static void systemConfigInit(String[] args) throws InstantiationException, IllegalAccessException{
		Field[] fields = SystemConfig.class.getDeclaredFields();
		for(int i=StringManager._0;i<args.length;i++){
			String str = args[i];
			if(StringManager.isEmpty(str) || str.split(StringManager._DH).length <= StringManager._1){
				continue;
			}
			execParameterMap.put(str.split(StringManager._DH)[StringManager._0], str.split(StringManager._DH)[StringManager._1]);
		}
		for(Field fidle : fields){
			if(execParameterMap.containsKey(fidle.getName())){
				Object value = StringManager.dataTypeConvert(fidle.getGenericType(),execParameterMap.get(fidle.getName()));
				if(value != null){
					fidle.setAccessible(true);
					fidle.set(systemConfig,value);
				}
			}
		}
	}
	
	/*按顺序注册handler*/
	private static void handlerRegisterInit(){
		handlerList.addAll(StringManager.getClasses(Application.class.getPackage().getName()));
		Iterator<Class<?>> iterators = handlerList.iterator();
		while(iterators.hasNext()){
			Class<?> iterator = iterators.next();
			if(!iterator.isAnnotationPresent(HandlerRegister.class)){
				iterators.remove();
			}
		}
		Collections.sort(handlerList,new Comparator<Class<?>>(){
			@Override
			public int compare(Class<?> o1, Class<?> o2) {
				if(o1.getAnnotation(HandlerRegister.class).loadingOrder() == o2.getAnnotation(HandlerRegister.class).loadingOrder()){
					return StringManager._0;
				}
				return o1.getAnnotation(HandlerRegister.class).loadingOrder() - o2.getAnnotation(HandlerRegister.class).loadingOrder();
			}
		});
		handlerList.forEach(handler -> logger.info(" register handler loading order : " + handler.getAnnotation(HandlerRegister.class).loadingOrder() + " ===>> " + handler.getName()));
		return ;
	}
	
	/*启动监听服务*/
	public static void start() throws InterruptedException{
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(bossGroup, workerGroup);
		bootstrap.channel(NioServerSocketChannel.class);
		try{
			bootstrap.childHandler(
				new ChannelInitializer<Channel>(){
					@Override
					protected void initChannel(Channel ch) throws Exception {
						ChannelPipeline pipeline  = ch.pipeline();
						for(Class<?> handler : handlerList){
							pipeline.addLast((ChannelHandler)handler.newInstance());
						}
					}
				}
			);
			
			/*服务器绑定端口监听*/
	        ChannelFuture f = bootstrap.bind(systemConfig.getHost(),systemConfig.getPort()).sync();
	        logger.info(" start successful host " + systemConfig.getHost() + " port " + systemConfig.getPort());
	        /*监听服务器关闭监听*/
	        f.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
	    }
		
	}
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, InterruptedException {
		
		/*1.外部参数映射初始化*/
		systemConfigInit(args);
		
		/*2.装载实体*/
		messageToEntityInit();
		
		/*3.装载handler*/
		handlerRegisterInit();
		
		/*4启动端口监听服务*/
		start();
	}
	
	
}
