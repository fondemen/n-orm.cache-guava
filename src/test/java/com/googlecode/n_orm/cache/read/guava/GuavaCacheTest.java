package com.googlecode.n_orm.cache.read.guava;

import com.googlecode.n_orm.cache.read.CacheTester;
import com.googlecode.n_orm.cache.read.ICache;
import com.googlecode.n_orm.cache.read.guava.GuavaCache;


public class GuavaCacheTest extends CacheTester{

	@Override
	public ICache createCache() {
		return new GuavaCache();
	}



}
