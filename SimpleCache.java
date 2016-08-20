package com.amit.project.cache;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleCache<K, V extends CacheValueWrapper<?>> {

	private Map<K, V> cacheMap = new ConcurrentHashMap<>();

	private int cacheLimit;

	private EvictionPolicy<K, V> evictionPolicy = new LRUEvictionPoilcy<K, V>();

	/**
	 * 
	 * @param limit
	 * @param evictionPolicy : if set as null default LRUEvictionPolicy will be used
	 */
	public SimpleCache(int limit, EvictionPolicy<K, V> evictionPolicy) {
		super();
		cacheLimit = limit;
		if (evictionPolicy != null) {
			this.evictionPolicy = evictionPolicy;
		}
	}

	public int getCacheLimit() {
		return cacheLimit;
	}

	public EvictionPolicy<K, V> getEvictionPolicy() {
		return evictionPolicy;
	}

	public void setCacheLimit(int cacheLimit) {
		if(cacheLimit < this.cacheLimit){
			throw new RuntimeException("Cache limit cannot be reduced as of now !!!");
		}
		this.cacheLimit = cacheLimit;
	}

	public void setEvictionPolicy(EvictionPolicy<K, V> evictionPolicy) {
		if (evictionPolicy == null) {
			throw new RuntimeException("Eviction policy cannot be set as null !!!");
		}
		this.evictionPolicy = evictionPolicy;
	}

	public V getValue(K key) {
		if (key == null) {
			return null;
		}
		V value = cacheMap.get(key);
		if (value == null) {
			return null;
		}
		value.refreshLastAccessedTime();
                if (value.hasExpired()) {
                        cacheMap.remove(key);
                        return null;
                }
		return value;
	}

	public synchronized void addToCache(K key, V value) {
		if (cacheLimit == 0) {
			return;
		}
		if (cacheMap.size() == cacheLimit) {
			K keyToEvict = evictionPolicy.getObjectToEvictFromSampledSet(cacheMap);
			cacheMap.remove(keyToEvict);
		}
		Date date = new Date();
		value.setCreationTime(date.getTime());
		cacheMap.put(key, value);
	}

}
