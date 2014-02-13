package com.googlecode.n_orm.cache.read.guava;

import com.googlecode.n_orm.cache.read.CacheException;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.*;
import com.googlecode.n_orm.cache.read.ICache;
import com.googlecode.n_orm.cf.ColumnFamily;
import com.googlecode.n_orm.storeapi.MetaInformation;

public class GuavaCache implements ICache {

	private Cache<String, Map<String, byte[]>> cache;

	public GuavaCache(long maxSize, long ttl, TimeUnit unit) {
		cache = CacheBuilder.newBuilder().maximumSize(maxSize)
				.expireAfterWrite(ttl, unit).build();

	}

	protected String getCacheKey(String table, String key, String familyName) {
		return table.concat(key).concat(familyName);
	}

	@Override
	public void delete(MetaInformation meta, String table, String key)
			throws CacheException {
		Collection<ColumnFamily<?>> cfs = meta.getElement().getColumnFamilies();
		String familyName = null;
		for (ColumnFamily<?> columnFamily : cfs) {
			familyName = columnFamily.getName();
			cache.invalidate(getCacheKey(table, key, familyName));
		}
	}

	@Override
	public void insertFamilyData(MetaInformation meta, String table,
			String key, String family, Map<String, byte[]> familyData)
			throws CacheException {
		String myKey = getCacheKey(table, key, family);
		cache.put(myKey, Collections
				.unmodifiableMap(new TreeMap<String, byte[]>(familyData)));
	}

	@Override
	public Map<String, byte[]> getFamilyData(MetaInformation meta,
			String table, String key, String family) throws CacheException {
		return cache.getIfPresent(getCacheKey(table, key, family));

	}

	@Override
	public long size() throws CacheException {
		return cache.size();

	}

	@Override
	public void reset() throws CacheException {
		cache.invalidateAll();
	}

	@Override
	public boolean existsData(MetaInformation meta, String table, String key,
			String family) throws CacheException {
		return this.getFamilyData(meta, table, key, family) != null;
	}

}
