package com.amit.project.cache;

public class SimpleCacheValueWrapper<T> extends CacheValueWrapper<T>{

	public SimpleCacheValueWrapper(T value) {
		super(value);
	}

	@Override
	public String toString() {
		return "SimpleCacheValueWrapper [getValue()=" + getValue() + "]";
	}

}
