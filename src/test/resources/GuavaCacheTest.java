import static org.junit.Assert.*;

import org.junit.Test;

import com.googlecode.n_orm.cache.read.guava.GuavaCache;


public class GuavaCacheTest extends TestCase{
private GuavaCache gc=new GuavaCache();

	public void testSize() throws Exception{
	int size=0;
	assertEquals(size, gc.size());
	}
	public void testGetMaximunSize() {
		assertEquals(10,gc.getMaximunSize());
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
