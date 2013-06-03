package com.gouglecode.n_orm.cache.read.hazelcast;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import com.googlecode.n_orm.cache.read.guava.CacheException;
import com.googlecode.n_orm.cache.read.guava.ICache;
import com.googlecode.n_orm.cf.ColumnFamily;
import com.googlecode.n_orm.storeapi.MetaInformation;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class HazelCastCache implements ICache{
	
	
	/**
	 * Hazelcast implements ICache AddThingsToCache, Remove, Insert ....
	 */
	
	private HazelcastInstance haz; 
	private ConcurrentMap<String, Map<String, byte[]>> FamilyData;
	
	public HazelCastCache(){
		Config cfg= new Config();
		haz=Hazelcast.getDefaultInstance();
		FamilyData=Hazelcast.getMap("Families");
		
	}
	/**
	 * Removes the mapping for a key from this map, if it's present
	 */
	@Override
	public void delete(MetaInformation meta, String table, String key)
			throws CacheException {
		// TODO Auto-generated method stub
		Collection<ColumnFamily<?>> cfs = meta.getElement().getColumnFamilies();
		String familyName = null;
		for (ColumnFamily<?> columnFamily : cfs) {
			familyName=columnFamily.getName();
			if(this.existsData(meta, table, key, familyName)){
				String myKey = table.concat(key).concat(familyName);
				FamilyData.remove(myKey);
				}
			}
	}
/**
 * Associates the specified value with the specified key in this class. 
 * If the class previously contained a mapping for the key, the old value is replaced by the specified
 * one.
 */
	@Override
	public void insertFamilyData(MetaInformation meta, String table,
			String key, String family, Map<String, byte[]> familyData)
			throws CacheException {
		String myKey=table.concat(key).concat(family);
		FamilyData.put(myKey, familyData);
	}
	/**
	 * Returns the value to which the specified key is mapped, or null if this class contains
	 * no mapping for the key
	 */
	@Override
	public Map<String, byte[]> getFamilyData(MetaInformation meta,
			String table, String key, String family) throws CacheException {
		// TODO Auto-generated method stub
		String myKey=table.concat(key).concat(family);
		return FamilyData.get(myKey);
	}
	/**
	 * Returns true if this class contains a mapping for the specified key
	 */
	@Override
	public boolean existsData(MetaInformation meta, String table, String id,
			String family) throws CacheException {
		String myKey=table.concat(id).concat(family);
		// TODO Auto-generated method stub
		return FamilyData.containsKey(myKey);
	}
	/**
	 * Returns the number of key-value mapping in this class
	 */
	@Override
	public long size() throws CacheException {
		// TODO Auto-generated method stub
		return FamilyData.size();
	}
	/**
	 * Remove all the values. The class will be empty after this class returns
	 */
	@Override
	public void reset() throws CacheException {
		// TODO Auto-generated method stub
		FamilyData.clear();
	}

	@Override
	public long getMaximunSize() throws CacheException {
		// TODO Auto-generated method stub
		return 0;
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