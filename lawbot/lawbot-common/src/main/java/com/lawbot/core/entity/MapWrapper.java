package com.lawbot.core.entity;

import java.util.HashMap;

/**
 * 
 * @author Cloud Lau
 *
 */
public class MapWrapper<K, V> extends HashMap<K, V> {

	public MapWrapper(){};
	
	public MapWrapper(K k , V v){
		super.put(k, v);
	}
	
	public MapWrapper<K, V> add(K k , V v){
		put(k ,v);
		return this;
	}
}
