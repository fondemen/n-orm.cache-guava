package com.googlecode.n_orm.cache.read.guava;

import com.googlecode.n_orm.storeapi.DelegatingStore;
import com.googlecode.n_orm.storeapi.Store;

public class CacheStore extends DelegatingStore{

	public CacheStore(Store actualStore) {
		super(actualStore);
		
	}

}
