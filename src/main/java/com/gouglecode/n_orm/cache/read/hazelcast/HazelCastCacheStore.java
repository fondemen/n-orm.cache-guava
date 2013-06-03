package com.gouglecode.n_orm.cache.read.hazelcast;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;

import com.googlecode.n_orm.DatabaseNotReachedException;
import com.googlecode.n_orm.cache.read.guava.CacheException;
import com.googlecode.n_orm.cache.read.guava.ICache;
import com.googlecode.n_orm.cf.ColumnFamily;
import com.googlecode.n_orm.storeapi.DefaultColumnFamilyData;
import com.googlecode.n_orm.storeapi.DelegatingStore;
import com.googlecode.n_orm.storeapi.MetaInformation;
import com.googlecode.n_orm.storeapi.Store;
import com.googlecode.n_orm.storeapi.Row.ColumnFamilyData;

public class HazelCastCacheStore extends DelegatingStore {
	private ICache hazCache;

	public HazelCastCacheStore(Store actualStore, ICache hazCache) {
		super(actualStore);
		this.hazCache=hazCache;
	}
	
	public void delete(MetaInformation meta, String table, String id) 
			throws DatabaseNotReachedException {
		
		Collection<ColumnFamily<?>> cfs = meta.getElement().getColumnFamilies();
		String family=cfs.getClass().getName();
		try {
			if(hazCache.existsData(meta, table, id, family)){
				hazCache.delete(meta, table, id);}
			else{
				System.out.println("No Data To Delete!");
				}
		} catch (CacheException e) {
			new DatabaseNotReachedException("The element can'nt be delete");
		}
	}
	
	/**
	 * check if an element exist in the cache
	 */
	public boolean exists(MetaInformation meta, String table, String row,
			String family) throws DatabaseNotReachedException {
		String key=table.concat(row).concat(family);
		try {
			return hazCache.existsData(meta, table, row, family);
		} catch (CacheException e) {
			new DatabaseNotReachedException("The element doesn't exist");
		}
		return false;
	}
	/**
	 * Check if an element is in the cache or in the store.
	 */
	
	public Map<String, byte[]> get(MetaInformation meta, String table,
			String id, String family) throws DatabaseNotReachedException {
		try{
		Map<String, byte[]> data= hazCache.getFamilyData(meta, table,id, family);
		if(data!=null){
			return data;
			}
		else{
			Map<String, byte[]> familyData=getActualStore().get(meta, table, id, family);
			hazCache.insertFamilyData(meta, table, id, family, familyData);
				assert familyData.equals(hazCache.getFamilyData(meta, table,id, family));
				return familyData;
			}
		} catch(CacheException e){
			new DatabaseNotReachedException("No family Data");
		}
		return null;
	}
	
	
	/**
	 * Check if a collection of element exist in the cache
	 */
	
	public boolean exists(MetaInformation meta, String table, String row)
			throws DatabaseNotReachedException {
		Collection<ColumnFamily<?>> cfs = meta.getElement().getColumnFamilies();
		for (ColumnFamily<?> columnFamily : cfs){
			String name=columnFamily.getName();
			try {
				if(hazCache.existsData(meta, table,row, name)){
					return true;
				 }
				else{
					return false;
					}
			} catch (CacheException e) {
				new DatabaseNotReachedException("The element doesn't exist in the cache");
			}
			}
		return false;
	}
	/**
	 * Returns a Column Family Data
	 */
	public ColumnFamilyData get(MetaInformation meta, String table, String id,
			Set<String> families) throws DatabaseNotReachedException {
		try {
			DefaultColumnFamilyData dcfd=new DefaultColumnFamilyData();
			Set<String> familiesName=new TreeSet<String>(families);
			Iterator it=familiesName.iterator();
			String name=null;
			Map<String, byte[]> data=new HashMap<String, byte[]>();
			
			while(it.hasNext()){
				name=(String)it.next();
				 data = hazCache.getFamilyData(meta, table, id, name);
				if(data!=null){
					dcfd.put(name, data);
					it.remove();
				}
			}
			ColumnFamilyData dataStore = getActualStore().get(meta, table, id, familiesName);
			
			for(Entry<String, Map<String, byte[]>> cfd: dataStore.entrySet()){
				dcfd.put(cfd.getKey(), cfd.getValue());
				hazCache.insertFamilyData(meta, table, id, cfd.getKey(), cfd.getValue());
			}
			return dcfd;
		} catch (CacheException e) {
			new DatabaseNotReachedException("");
		}
		return null;
	}

}
