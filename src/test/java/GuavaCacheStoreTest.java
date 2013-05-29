import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.googlecode.n_orm.cache.read.guava.CacheException;
import com.googlecode.n_orm.cache.read.guava.GuavaCacheStore;
import com.googlecode.n_orm.cache.read.guava.ICache;
import com.googlecode.n_orm.conversion.ConversionTools;
import com.googlecode.n_orm.storeapi.DefaultColumnFamilyData;
import com.googlecode.n_orm.storeapi.MetaInformation;
import com.googlecode.n_orm.storeapi.Row.ColumnFamilyData;
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
	@Test
	public void testExist() throws CacheException{
		GuavaCacheStore gcs=new GuavaCacheStore(mockStore, mockCache);
		expect(mockCache.existsData(null, "table", "key", "family")).andReturn(true);
		replay(mockCache);
		sut.exists(null, "table", "key", "family");
		verify(mockCache);
	}
	@Test
	public void testExists() throws CacheException{
		DefaultColumnFamilyData dcfd=new DefaultColumnFamilyData();
		//GuavaCacheStore gcs=new GuavaCacheStore(mockStore, mockCache);
		Set<String> families=new HashSet<String>();
		families.add("family");
		families.add("f");
		families.add("fam");
		families.add("Toto");
		families.add("Tata");
		families.add("Titi");
		Map<String, byte[]> value = new HashMap<String, byte[]>();
		value.put("Toto", new byte[10]);
		
		expect(mockCache.getFamilyData(null, "table", "id", "family")).andReturn(value);
		expect(mockCache.getFamilyData(null, "table", "id", "f")).andReturn(value);
		expect(mockCache.getFamilyData(null, "table", "id", "fam")).andReturn(value);
		
		ColumnFamilyData v = new DefaultColumnFamilyData();
		v.put("Toto", value);
		v.put("Tata", value);
		v.put("Titi", value);
		expect(mockStore.get(null, "table", "id", families)).andReturn(v);
		mockCache.insertFamilyData(null, "table", "id", "family", v.get("Toto"));
		mockCache.insertFamilyData(null, "table", "id", "family", v.get("Tata"));
		mockCache.insertFamilyData(null, "table", "id", "family", v.get("Titi"));
		replay(mockStore,mockCache);
		sut.get(null, "table", "id", families);
		verify(mockStore,mockCache);

	}

}
