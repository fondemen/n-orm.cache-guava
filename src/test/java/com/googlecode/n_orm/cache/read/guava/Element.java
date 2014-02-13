package com.googlecode.n_orm.cache.read.guava;

import com.googlecode.n_orm.Key;
import com.googlecode.n_orm.Persisting;

@Persisting
public class Element {
	@Key public String key;
	
	public String value;
}
