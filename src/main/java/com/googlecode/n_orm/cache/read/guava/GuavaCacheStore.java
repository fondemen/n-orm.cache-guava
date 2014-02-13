package com.googlecode.n_orm.cache.read.guava;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.googlecode.n_orm.cache.read.CachedStore;
import com.googlecode.n_orm.cache.read.ICache;
import com.googlecode.n_orm.storeapi.Store;


public class GuavaCacheStore extends CachedStore {
	
	private long maxSize = Long.MAX_VALUE;
	private long ttl = Long.MAX_VALUE;
	private TimeUnit unit = TimeUnit.MILLISECONDS;

	public GuavaCacheStore(Store actualStore) {
		super(actualStore);
	}

	/**
	 * The maximum number of elements that can be held by the cache.
	 * Default value is {@link Long#MAX_VALUE}.
	 * @see CacheBuilder#maximumSize(long)
	 */
	public long getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(long maxSize) {
		if (maxSize == this.maxSize) {
			return;
		}
		
		if (maxSize <= 0) {
			throw new IllegalArgumentException("Max size of a cache must be positive");
		}
		
		if (this.getCache() != null) {
			throw new IllegalArgumentException("Cannot set max size after Guava cache was constructed");
		}
		
		this.maxSize = maxSize;
	}

	/**
	 * The time to live for elements that held by the cache.
	 * Default value is {@link Long#MAX_VALUE}.
	 * @see CacheBuilder#expireAfterWrite(long, TimeUnit)
	 */
	public long getTtl() {
		return ttl;
	}

	/**
	 * @param ttl time to live for a given {{@link #getUnit() unit}
	 * @see CacheBuilder#expireAfterWrite(long, TimeUnit)
	 */
	public void setTtl(long ttl) {
		if (ttl == this.ttl) {
			return;
		}
		
		if (ttl <= 0) {
			throw new IllegalArgumentException("Time to live of a cache must be positive");
		}
		
		if (this.getCache() != null) {
			throw new IllegalArgumentException("Cannot set max size after Guava cache was constructed");
		}
		
		this.ttl = ttl;
	}

	/**
	 * The time to live unit for elements that held by the cache.
	 * Default value is {@link TimeUnit#MILLISECONDS}.
	 * @see CacheBuilder#expireAfterWrite(long, TimeUnit)
	 */
	public TimeUnit getUnit() {
		return unit;
	}

	public void setUnit(TimeUnit unit) {
		if (unit == null) {
			throw new NullPointerException();
		}
		
		if (unit == this.unit) {
			return;
		}
		
		if (this.getCache() != null) {
			throw new IllegalArgumentException("Cannot set max size after Guava cache was constructed");
		}
		
		this.unit = unit;
	}

	@Override
	protected ICache createCache() {
		return new GuavaCache(this.getMaxSize(), this.getTtl(), this.getUnit());
	}
	
}

