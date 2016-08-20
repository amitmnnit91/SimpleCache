package com.amit.project.cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class CacheValueWrapper<T> {

	private T value;

	private long creationTime;

	private List<Long> accessTimes = new ArrayList<>();

	public CacheValueWrapper(T value) {
		super();
		this.value = value;
	}

	public final void refreshLastAccessedTime() {
		Date date = new Date();
		accessTimes.add(Long.valueOf(date.getTime()));
	}

	public final int getAccessFrequency() {
		return accessTimes.size();
	}

	public final Long getLastAccessedTime() {
		int accessFreq = getAccessFrequency();
		if (accessFreq > 0) {
			return accessTimes.get(accessFreq - 1);
		}
		return creationTime;
	}

	public final T getValue() {
		return value;
	}

	public final List<Long> getAccessTimes() {
		return accessTimes;
	}

	public final long getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}

	// Hook method, can be overridden in any subclass of this class
	public boolean hasExpired() {
		return false;
	}

}
