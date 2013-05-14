package cache.read;

public class GuavaCache implements Cache{

	private static  long MAX_SIZE=10;
	private static long TTL=2;
	
	

	public long getMaximunSize() throws Exception {
		return MAX_SIZE;
	}
	public void setMaximunSize(long size) throws Exception {
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
