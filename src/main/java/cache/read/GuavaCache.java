package cache.read;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Table;
import com.google.common.cache.*;

public class GuavaCache implements ICache{

	private static  int MAX_SIZE=10;
	private static long TTL=2;
	private final Cache<String, CachePopulating> cache;
	
	public GuavaCache(){
		cache=CacheBuilder.newBuilder().maximumSize(MAX_SIZE)
										.expireAfterWrite(TTL, TimeUnit.SECONDS)
										.build(
												new CacheLoader<String, CachePopulating>(){
													public CachePopulating load(String key) throws Exception{
														return new CachePopulating(key);
												}
												});
									   
	}
	
	/**
	 * Returns the maximum size of our cache
	 */
	public long getMaximunSize() throws Exception {
		return MAX_SIZE;
	}
	public void setMaximunSize(int size) throws Exception {
		this.MAX_SIZE=size;
		
	}
	public long getTTL() throws Exception {
		return TTL;
	}
	public void setTTL(long TTL) throws Exception {
		this.TTL=TTL;
		
	}
	public String getKey() throws Exception {
		return null;
	}


}
