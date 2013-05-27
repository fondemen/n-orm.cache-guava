package com.googlecode.n_orm.cache.read.guava;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.googlecode.n_orm.DatabaseNotReachedException;
import com.googlecode.n_orm.cf.ColumnFamily;
import com.googlecode.n_orm.storeapi.DefaultColumnFamilyData;
import com.googlecode.n_orm.storeapi.DelegatingStore;
import com.googlecode.n_orm.storeapi.MetaInformation;
import com.googlecode.n_orm.storeapi.Store;
import com.googlecode.n_orm.storeapi.Row.ColumnFamilyData;

public class GuavaCacheStore extends DelegatingStore{
	private GuavaCache cache=new GuavaCache();
	

	public GuavaCacheStore(Store actualStore, GuavaCache gc) {
		super(actualStore);
		this.cache=gc;
	}
	public void delete(MetaInformation meta, String table, String id) {
		Collection<ColumnFamily<?>> cfs = meta.getElement().getColumnFamilies();
		String family=cfs.getClass().getName();
		try {
			if(cache.existsData(meta, table, id, family)){
					cache.delete(meta, table, id);}
			else{
				System.out.println("No Data To Delete!");
				}
		} catch (CacheException e) {
			new DatabaseNotReachedException("The element can'nt be delete");
		}
			
	}
	
	public boolean exists(MetaInformation meta, String table, String row) {
		Collection<ColumnFamily<?>> cfs = meta.getElement().getColumnFamilies();
		String family=cfs.getClass().getName();
		try {
			return cache.existsData(meta, table, row, family);
		} catch (CacheException e) {
			new DatabaseNotReachedException("The element does'nt exist in the cache");
		}
		return false;
	}
	
	public Map<String, byte[]> get(MetaInformation meta, String table,
			String id, String family)  {
		try{
		Map<String, byte[]> data= cache.getFamilyData(meta, table,id, family);
		if(data!=null){
			return data;
			}
		else{
			Map<String, byte[]> familyData=getActualStore().get(meta, table, id, family);
			cache.insertFamilyData(meta, table, id, family, familyData);
				assert familyData.equals(cache.getFamilyData(meta, table,id, family));
				return familyData;
			}
		} catch(CacheException e){
			new DatabaseNotReachedException("No family Data");
		}
		return null;
	}

	public ColumnFamilyData get(MetaInformation meta, String table, String id,
			Set<String> families) throws DatabaseNotReachedException {
		DefaultColumnFamilyData dcfd=new DefaultColumnFamilyData();
		ArrayList<String> list=new ArrayList<String>();
		list=(ArrayList<String>) families;
		for(int i=0; i<list.size();i++){
			dcfd.put(list.get(i), get(meta, table,id, list.get(i))); }
		return dcfd;
	}	
		
}

