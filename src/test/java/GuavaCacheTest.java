import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.googlecode.n_orm.cache.read.guava.GuavaCache;


public class GuavaCacheTest {
	private GuavaCache gc=new GuavaCache();

	@Before
	public void setUp() throws Exception {
	}
	@After
	public void tearDown() throws Exception {
	}

	/*@Test
	public void test() {
		fail("Not yet implemented");
	}*/
	@Test
	public void testGetSize() throws Exception{
		long size=0;
		assertEquals(size, gc.size());
	}

}
