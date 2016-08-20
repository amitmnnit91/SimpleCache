package com.amit.project.cache;

import java.util.Map;

@SuppressWarnings("rawtypes")
public abstract class EvictionPolicy<K,V extends CacheValueWrapper> {

	private String policyName;

	public EvictionPolicy(String policyName) {
		super();
		this.policyName = policyName;
	}

	public String getPolicyName() {
		return policyName;
	}
	
	// Declaring it as a hook method so that implementation can be changed as per need
	public K getObjectToEvictFromSampledSet(Map<K, V> cacheMap){
		K evictObj = null;
		for(K obj : cacheMap.keySet()){
			if(evictObj == null){
				evictObj = obj;
				continue;
			}
			boolean evictionObjChanged = compare(cacheMap.get(evictObj), cacheMap.get(obj));
			if(evictionObjChanged){
				evictObj = obj;
			}
		}
		return evictObj;
	}
	
	/**
	 * 
	 * @param actObject : Object used as reference for comparison
	 * @param compObject : Object to compare
	 * @return true if compObject has higher chance of eviction in comparison to actObject
	 */
	public abstract boolean compare(V actObject, V compObject);
}
