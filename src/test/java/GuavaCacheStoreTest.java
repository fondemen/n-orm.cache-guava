import static org.junit.Assert.*;
import com.googlecode.n_orm.cache.read.CacheException;
import com.googlecode.n_orm.cache.read.Element;
import com.googlecode.n_orm.cache.read.ICache;
import static org.easymock.EasyMock.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import com.googlecode.n_orm.cache.read.guava.GuavaCacheStore;
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
		mockCache=createMock(com.googlecode.n_orm.cache.read.ICache.class);
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
		Element e=new Element();
		e.key="tagada";
		e.familyName="tsoin tsoin";
		e.familyData.put("A", 12);
		e.familyData.put("B", 14);
		MetaInformation meta=new MetaInformation();
		expect(mockCache.existsData(meta, "table", "key", "family")).andReturn(true);
		replay(mockCache);
		sut.exists(meta, "table", "key", "family");
		verify(mockCache);
	}
	@Test
	public void testDelete()throws CacheException{
		Element e=new Element();
		e.key="tagada";
		e.familyName="tsoin tsoin";
		e.familyData.put("A", 12);
		e.familyData.put("B", 14);
		MetaInformation meta=new MetaInformation();
		expect(mockCache.existsData(meta, "table", "key", "familyData")).andReturn(true);
		mockCache.delete(meta, "table", "key");
		replay(mockCache);
		sut.delete(meta, "table", "key");
		verify(mockCache);
	}
	/*public void testExists() throws CacheException{
		Element e=new Element();
		e.key="tagada";
		e.familyName="tsoin tsoin";
		e.familyData.put("A", 12);
		e.familyData.put("B", 14);
		MetaInformation meta=new MetaInformation();
		expect(mockCache.existsData(meta, "table", "row")).andReturn(true);
		replay(mockCache);
		sut.exists(meta, "table", "row");
		verify(mockCache);
	}
	*/
		
	public void testGets(Set<String> cachedFamilies,Set<String>storedFamilies) throws CacheException{
		Set<String> families=new HashSet<String>();
		families.addAll(cachedFamilies);
		families.addAll(storedFamilies);
		sut.get(null, "table", "id", families);
	}
	
	@Test
	public void testDataStore() throws CacheException/*All data are from the store*/{
		Set<String> storedFamilies=new HashSet<String>();
		storedFamilies.add("f1Store");
		storedFamilies.add("f2Store");
		storedFamilies.add("f3Store");
		expect(mockCache.getFamilyData(null, "table", "id", "f1Store")).andReturn(null);
		expect(mockCache.getFamilyData(null, "table", "id", "f2Store")).andReturn(null);
		expect(mockCache.getFamilyData(null, "table", "id", "f3Store")).andReturn(null);
		
		ColumnFamilyData v = new DefaultColumnFamilyData();
		Map<String, byte[]> value = new HashMap<String, byte[]>();
		value.put("Toto", new byte[10]);
		expect(mockStore.get(null, "table", "id", storedFamilies)).andReturn(v);
		for (String sf : storedFamilies) {
			v.put(sf, value);
			mockCache.insertFamilyData(null, "table", "id", sf, value);
		}
		replay(mockCache,mockStore);
		sut.get(null, "table", "id", storedFamilies);
		verify(mockStore,mockCache);
	}
	
	@Test
	public void testDataCache() throws CacheException/*All data are from the cache, Nothing from the store*/{
		Set<String> cachedFamilies = new HashSet<String>();
		Set<String> families=new HashSet<String>();
		Map<String, byte[]> value=new HashMap<String,byte[]>();
		value.put("Toto", new byte[13]);
		cachedFamilies.add("f1Cache");
		cachedFamilies.add("f2Cache");
		cachedFamilies.add("f3Cache");
		families.addAll(cachedFamilies);	
		
		/*expect(mockCache.getFamilyData(null, "table", "id", "f1Cache")).andReturn(value);
		expect(mockCache.getFamilyData(null, "table", "id", "f2Cache")).andReturn(value);
		expect(mockCache.getFamilyData(null, "table", "id", "f3Cache")).andReturn(value);*/
		Iterator it =cachedFamilies.iterator();
		String name=null;
		while(it.hasNext()){
			name=(String)it.next();
			expect(mockCache.getFamilyData(null, "table", "id", name.toString())).andReturn(value);
			expect(mockCache.getFamilyData(null, "table", "id", name.toString())).andReturn(value);
			expect(mockCache.getFamilyData(null, "table", "id", name.toString())).andReturn(value);
		}
		replay(mockCache,mockStore);
		ColumnFamilyData v = sut.get(null, "table", "id", cachedFamilies);
		System.out.println(v.toString());
		verify(mockCache,mockStore);
	}
	@Test
	public void test() throws CacheException{
		DefaultColumnFamilyData dcfd=new DefaultColumnFamilyData();
		Set<String> cachedFamilies = new HashSet<String>();
		cachedFamilies.add("f1Cache");
		cachedFamilies.add("f2Cache");
		cachedFamilies.add("f3Cache");
		Set<String> storedFamilies = new HashSet<String>();
		storedFamilies.add("f1Store");
		storedFamilies.add("f2Store");
		storedFamilies.add("f3Store");
		Set<String> families=new HashSet<String>();
		families.addAll(cachedFamilies);
		families.addAll(storedFamilies);
		Map<String, byte[]> value = new HashMap<String, byte[]>();
		value.put("Toto", new byte[10]);
		
		expect(mockCache.getFamilyData(null, "table", "id", "f1Cache")).andReturn(value);
		expect(mockCache.getFamilyData(null, "table", "id", "f2Cache")).andReturn(value);
		expect(mockCache.getFamilyData(null, "table", "id", "f3Cache")).andReturn(value);
		expect(mockCache.getFamilyData(null, "table", "id", "f1Store")).andReturn(null);
		expect(mockCache.getFamilyData(null, "table", "id", "f2Store")).andReturn(null);
		expect(mockCache.getFamilyData(null, "table", "id", "f3Store")).andReturn(null);
		
		ColumnFamilyData v = new DefaultColumnFamilyData();
		for (String sf : storedFamilies) {
			v.put(sf, value);
			mockCache.insertFamilyData(null, "table", "id", sf, value);
		}
		expect(mockStore.get(null, "table", "id", storedFamilies)).andReturn(v);
		replay(mockStore,mockCache);
		sut.get(null, "table", "id", families);
		verify(mockStore,mockCache);

		
	}
	
	

}
