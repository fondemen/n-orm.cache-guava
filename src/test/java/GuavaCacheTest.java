import static org.junit.Assert.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.googlecode.n_orm.cache.read.guava.CacheException;
import com.googlecode.n_orm.cache.read.guava.GuavaCache;
import com.googlecode.n_orm.cache.write.FixedThreadPool;
import com.googlecode.n_orm.conversion.ConversionTools;


public class GuavaCacheTest {
	@Test
	public void testGetSize() throws Exception{
		GuavaCache gc=new GuavaCache();
		long size=0;
		assertEquals(size, gc.size());
	}
	@Test
	public void testGetMaximunSize() throws CacheException{
		GuavaCache gc=new GuavaCache();
		gc.setMaximunSize(200);
		assertEquals(200, gc.getMaximunSize());
	}
	@Test
	public void testGetTTL() throws CacheException {
		GuavaCache gc=new GuavaCache();
		gc.setTTL(20);
		assertEquals(20, gc.getTTL());
	}
	@Test
	public void testSetTTL() throws CacheException {
		GuavaCache gc=new GuavaCache();
		gc.setTTL(20);
		assertEquals(20, gc.getTTL());
		assertNotSame(10, gc.getTTL());
	}
	@Test
	public void testSetMaximunSize() throws CacheException{
		GuavaCache gc=new GuavaCache();
		gc.setMaximunSize(200);
		assertEquals(200, gc.getMaximunSize());
	}
	
}
