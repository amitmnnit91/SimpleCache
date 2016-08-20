package com.amit.project.cache;

public class LRUEvictionPoilcy<K,V extends CacheValueWrapper<?>> extends EvictionPolicy<K,V> {

	public LRUEvictionPoilcy() {
		super("LRU");
	}

	@Override
	public boolean compare(V actObject,V compObject) {
		if(compObject.getLastAccessedTime() > actObject.getLastAccessedTime()){
			return true;
		}
		return false;
	}

}
