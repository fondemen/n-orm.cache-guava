package cache.read;

public interface Cache {
	/**
	 * Defines what a cache is.
	 */
	
	public String getKey() throws Exception;
	public long getMaximunSize() throws Exception;
	public void setMaximunSize(long size) throws Exception;
	public long getTTL() throws Exception;
	public void setTTL(long TTL) throws Exception;

}
