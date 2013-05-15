package com.googlecode.n_orm.cache.read.guava;

import java.util.ArrayList;



public class Caches {
	private ArrayList<GuavaCache> caches =new ArrayList<GuavaCache>();
	
	public Caches(){
		caches.clear();
	}
	
	/**
	 * Add  a cache in the list of the cache
	 */
	public void addCache(){
		GuavaCache gc=new GuavaCache();
		caches.add(gc);
	}
	/**
	 * Delete one cache in the list of the cache
	 */
	public void deleteCache( GuavaCache gc){
		caches.remove(gc);
	}
	/**
	 * Checks whether the a cache exists
	 */
	public boolean hasCache(ArrayList<GuavaCache> c){
		return c.isEmpty();
	}
	/**
	 * returns the number of cache in the list
	 */
	public int getNumberCache(){
		return caches.size();
	}

}
