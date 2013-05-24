package com.googlecode.n_orm.cache.read.guava;

import java.util.Map;
import java.util.Set;

import com.googlecode.n_orm.DatabaseNotReachedException;
import com.googlecode.n_orm.storeapi.CloseableKeyIterator;
import com.googlecode.n_orm.storeapi.Constraint;
import com.googlecode.n_orm.storeapi.MetaInformation;
import com.googlecode.n_orm.storeapi.Row.ColumnFamilyData;
import com.googlecode.n_orm.storeapi.Store;

public class CacheStore implements Store{
	// Mon cacheStore connait et utilise un cache
	private GuavaCache ActualCache= new GuavaCache();

	@Override
	public void start() throws DatabaseNotReachedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasTable(String tableName)
			throws DatabaseNotReachedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void delete(MetaInformation meta, String table, String id) // Here id is the key
			throws DatabaseNotReachedException {
		try {
			ActualCache.delete(meta, table, id);
		} catch (CacheException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean exists(MetaInformation meta, String table, String row)
			throws DatabaseNotReachedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean exists(MetaInformation meta, String table, String row,
			String family) throws DatabaseNotReachedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CloseableKeyIterator get(MetaInformation meta, String table,
			Constraint c, int limit, Set<String> families)
			throws DatabaseNotReachedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] get(MetaInformation meta, String table, String row,
			String family, String key) throws DatabaseNotReachedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, byte[]> get(MetaInformation meta, String table,
			String id, String family) throws DatabaseNotReachedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, byte[]> get(MetaInformation meta, String table,
			String id, String family, Constraint c)
			throws DatabaseNotReachedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ColumnFamilyData get(MetaInformation meta, String table, String id,
			Set<String> families) throws DatabaseNotReachedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void storeChanges(MetaInformation meta, String table, String id,
			ColumnFamilyData changed, Map<String, Set<String>> removed,
			Map<String, Map<String, Number>> increments)
			throws DatabaseNotReachedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long count(MetaInformation meta, String table, Constraint c)
			throws DatabaseNotReachedException {
		// TODO Auto-generated method stub
		return 0;
	}

}
