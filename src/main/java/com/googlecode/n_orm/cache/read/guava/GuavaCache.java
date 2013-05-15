package com.googlecode.n_orm.cache.read.guava;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.cache.*;
import com.googlecode.n_orm.storeapi.MetaInformation;

public class GuavaCache implements ICache{

	private static  int MAX_SIZE=10;
	private static long TTL=2;
	private final Cache<String, Table> cache;
	private Table<String, String, Map<String, byte[]>> tab;
	
	public GuavaCache(){
		cache=CacheBuilder.newBuilder().maximumSize(MAX_SIZE)
										.expireAfterWrite(TTL, TimeUnit.SECONDS)
										.build();
		tab= HashBasedTable.create();
									   
	}

	public void delete(MetaInformation meta, String table, String key)
			throws CacheException {
		String myKey=table.concat(key);
		cache.invalidate(myKey);
		
	}

	public void insertFamilyData(MetaInformation meta, String table,
			String key, String family, Map<String, byte[]> familyData)
			throws CacheException {
		String myKey=table.concat(key);
		tab.put(table, key, familyData);
		cache.put(myKey, tab);
	}
	
	public Map<String, byte[]> getFamilyData(MetaInformation meta,
			String table, String key) throws CacheException {
		String myKey=table.concat(key);
		Table<String, String, Map<String, byte[]>> atab= cache.getIfPresent(myKey);  // me retourne la table correspondant à cette clef
		return atab.get(table, key);
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

	@Override
	public void setMaximunSize(int size) throws CacheException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getTTL() throws CacheException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTTL(long TTL) throws CacheException {
		// TODO Auto-generated method stub
		
	}
	
	
}
