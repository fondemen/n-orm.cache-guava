import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.googlecode.n_orm.Key;
import com.googlecode.n_orm.KeyManagement;
import com.googlecode.n_orm.Persisting;
import com.googlecode.n_orm.storeapi.Store;


public class StoreCacheTest {
	private static final String table="testtable";
	private static final String hash="trololo";
	private static final String id=hash+KeyManagement.KEY_SEPARATOR;
	
	@Persisting(table=table)
	public static class Element{
		@Key public String key;
		public String prop;
		public Map<String, byte[]> families=new HashMap<String, byte[]>();
		
		public Element(String key){
			super();
			this.key=key;
		}
	}
	
	/*private Element element;;
	@Mock Store store;	

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}*/

}
