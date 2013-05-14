package cache.read;

public interface ICache {
	/**
	 * Defines what a cache is.
	 */
	/**
	 * Check if an element exists in the cache using the key
	 */
	public boolean exists(String key) throws Exception;
	/**
	 * delete an element in the cache using the key
	 */
	public void delete(String key) throws Exception;
	/**
	 * To insert an element in the cache
	 */
	public void insert(String key, Object obj) throws Exception;
	/**
	 * Return an element of the cache using  the key
	 */
	public Object get(String key) throws Exception;
	/**
	 * return the the approximate number of entries in the cache
	 */
	public long size() throws Exception;
	/**
	 * delete all the element in the cache
	 */
	public void deleteAll() throws Exception;
	/**
	 * returns the maximum number of elements that can contain cache
	 */
	public long getMaximunSize() throws Exception;
	/**
	 * Change the maximum of elements that can contain cache
	 */
	public void setMaximunSize(int size) throws Exception;
	/**
	 * returns the life of elements contained in the cache
	 */
	public long getTTL() throws Exception;
	/**
	 * for change the life of element in the cache
	 */
	public void setTTL(long TTL) throws Exception;

}
