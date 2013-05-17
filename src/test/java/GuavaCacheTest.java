import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.googlecode.n_orm.cache.read.guava.GuavaCache;


public class GuavaCacheTest extends TestCase {
	
	private static int MAX_SIZE;
	private static long TTL;
	private GuavaCache gc;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		MAX_SIZE=10;
		TTL=2;
	}

	@After
	public void tearDown() throws Exception {
		MAX_SIZE=0;
		TTL=0;
	}
	public void testSize() throws Exception{
		long s=0;
		assertEquals(s, gc.size());
		}

	/*@Test
	public void test() {
		fail("Not yet implemented");
	}*/

}
