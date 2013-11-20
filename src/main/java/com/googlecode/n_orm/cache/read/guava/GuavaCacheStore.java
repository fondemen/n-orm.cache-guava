package com.googlecode.n_orm.cache.read.guava;
import com.googlecode.n_orm.cache.read.CacheException;
import com.googlecode.n_orm.cache.read.CachedStore;
import com.googlecode.n_orm.storeapi.Store;


public class GuavaCacheStore extends CachedStore {

	public GuavaCacheStore(Store actualStore) {
		super(actualStore, new GuavaCache());
		
	}
	
	public long getTTLMs() {
		try {
			return ((GuavaCache)this.getCache()).getTTL();
		} catch (CacheException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void setTTLMs(long ttlms) {
		try {
			((GuavaCache)this.getCache()).setTTL(ttlms);
		} catch (CacheException e) {
			throw new RuntimeException(e);
		}
	}
	
	
}

