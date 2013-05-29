import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.googlecode.n_orm.cache.read.guava.CacheException;
import com.googlecode.n_orm.cache.read.guava.GuavaCacheStore;
import com.googlecode.n_orm.cache.read.guava.ICache;
import com.googlecode.n_orm.conversion.ConversionTools;
import com.googlecode.n_orm.storeapi.Store;


public class GuavaCacheStoreTest {
	private Store mockStore;
	private ICache mockCache;
	private GuavaCacheStore sut;

	@Before
	public void setUp() throws Exception {
		mockStore=createMock(Store.class);
		mockCache=createMock(ICache.class);
		sut=new GuavaCacheStore(mockStore,mockCache);
		
	}
	@Test
	public void testGet() throws CacheException {
		expect(mockCache.getFamilyData(null, "table", "key", "family")).andReturn(null);
		Map<String, byte[]> familyData=new HashMap<String,byte[]>();
		byte[] Dupond = ConversionTools.convert("Dupont");
		byte[] Jean = null;
		byte[] age= ConversionTools.convert("10");
		familyData.put("nom", Dupond);
		familyData.put("Prenom", Jean);
		familyData.put("age", age);
		expect(mockStore.get(null, "table", "key", "family")).andReturn(familyData);
		mockCache.insertFamilyData(null, "table", "key", "family", familyData);
		replay(mockStore,mockCache);
		sut.get(null, "table", "key", "family");
		verify(mockCache);
		
	}

}
