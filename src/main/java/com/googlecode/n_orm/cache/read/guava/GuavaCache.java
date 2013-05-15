package com.googlecode.n_orm.cache.read.guava;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.cache.*;
import com.googlecode.n_orm.cf.ColumnFamily;
import com.googlecode.n_orm.storeapi.MetaInformation;

public class GuavaCache implements ICache {

	private static int MAX_SIZE = 10;
	private static long TTL = 2;
	private final Cache<String,  Map<String, byte[]>> cache;

	public GuavaCache() {
		cache = CacheBuilder.newBuilder().maximumSize(MAX_SIZE)
				.expireAfterWrite(TTL, TimeUnit.SECONDS).build();

	}

	public void delete(MetaInformation meta, String table, String key)
			throws CacheException {
		String myKey = table.concat(key);
		cache.invalidate(myKey);
		
		Collection<ColumnFamily<?>> cfs = meta.getElement().getColumnFamilies();/*Retourne toute les familles,
		 et avec getName pour avoir le nom de la famille*/
		

	}

	public void insertFamilyData(MetaInformation meta, String table,
			String key, String family, Map<String, byte[]> familyData)
			throws CacheException {
		String myKey=table.concat(key).concat(family);
		cache.put(myKey, familyData);
	}

	public Map<String, byte[]> getFamilyData(MetaInformation meta,
			String table, String key,String family) throws CacheException {
		String myKey=table.concat(key).concat(family);
				return cache.getIfPresent(myKey);
		
	}

	public long size() throws CacheException {
		return cache.size();
	}

	public void reset() throws CacheException {
		cache.invalidateAll();
	}

	public long getMaximunSize() throws CacheException {
		return MAX_SIZE;

	}
	public void setMaximunSize(int size) throws CacheException {
		this.MAX_SIZE=size;

	}
	public long getTTL() throws CacheException {
		return TTL;
	}
	public void setTTL(long TTL) throws CacheException {
		this.TTL=TTL;

	}

}
