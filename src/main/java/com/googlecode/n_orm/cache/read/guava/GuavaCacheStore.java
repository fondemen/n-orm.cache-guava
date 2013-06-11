package com.googlecode.n_orm.cache.read.guava;
import com.googlecode.n_orm.cache.read.CacheException;
import com.googlecode.n_orm.cache.read.CachedStore;
import com.googlecode.n_orm.cache.read.ICache;
import com.googlecode.n_orm.storeapi.Store;


public class GuavaCacheStore extends CachedStore{

	public GuavaCacheStore(Store actualStore, ICache cache) {
		super(actualStore, cache);
		
	}
	
	
}

