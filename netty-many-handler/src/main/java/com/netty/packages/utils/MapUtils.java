package com.netty.packages.utils;
import java.util.HashMap;
import java.util.Map;

public class MapUtils<T,T1> extends HashMap<T,T1> {
	
	private static final long serialVersionUID = 1L;

	public MapUtils<T,T1> setData(T key, T1 value){
		super.put(key, value);
		return this;
	}
	
	public MapUtils<T,T1> setDataAll(Map<T,T1> value){
		super.putAll(value);
		return this;
	}
	
}