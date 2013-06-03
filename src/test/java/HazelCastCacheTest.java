import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.googlecode.n_orm.cache.read.guava.CacheException;
import com.googlecode.n_orm.cache.read.guava.GuavaCache;
import com.googlecode.n_orm.cf.ColumnFamily;
import com.googlecode.n_orm.storeapi.MetaInformation;
import com.gouglecode.n_orm.cache.read.hazelcast.HazelCastCache;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;


public class HazelCastCacheTest {

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void insertFamilyDataTest() throws CacheException{
		HazelCastCache  hazCache=new HazelCastCache();
		Map<String, byte[]> familyData =new HashMap<String, byte[]>();
		familyData.put("Toto", new byte[0]);
		hazCache.insertFamilyData(null, "Person", "290", "props", familyData);
		System.out.println(hazCache.size());
		assertEquals(1, hazCache.size());
	}
	
	@Test
	public void getFamilyDataTest() throws CacheException {
		HazelCastCache  hazCache=new HazelCastCache();
		Map<String, byte[]> familyData =new HashMap<String, byte[]>();
		familyData.put("Toto", new byte[0]);
		hazCache.insertFamilyData(null, "Person", "290", "props", familyData);
		//assertEquals(familyData, hazCache.getFamilyData(null, "Person", "290", "props"));
	}
	@Test
	public void existsDataTest() throws CacheException{
		HazelCastCache  hazCache=new HazelCastCache();
		Map<String, byte[]> familyData =new HashMap<String, byte[]>();
		familyData.put("Toto", new byte[0]);
		hazCache.insertFamilyData(null, "Person", "290", "props", familyData);
		assertTrue(hazCache.existsData(null, "Person", "290", "props"));
	}
	@Test
	public void sizeTest() throws CacheException{
		HazelCastCache  hazCache=new HazelCastCache();
		Map<String, byte[]> FirstFamilyData =new HashMap<String, byte[]>();
		Map<String, byte[]> SecondFamilyData =new HashMap<String, byte[]>();
		FirstFamilyData.put("Toto", new byte[0]);
		SecondFamilyData.put("Titi", new byte[1]);
		hazCache.insertFamilyData(null, "Person", "290", "props", FirstFamilyData);
		hazCache.insertFamilyData(null, "Persons", "1000", "prop", SecondFamilyData);
		assertEquals(2, hazCache.size());
	}
	
	@Test
	public void resetTest() throws CacheException{
		HazelCastCache  hazCache=new HazelCastCache();
		Map<String, byte[]> FirstFamilyData =new HashMap<String, byte[]>();
		Map<String, byte[]> SecondFamilyData =new HashMap<String, byte[]>();
		FirstFamilyData.put("Toto", new byte[0]);
		SecondFamilyData.put("Titi", new byte[1]);
		hazCache.insertFamilyData(null, "Person", "290", "props", FirstFamilyData);
		hazCache.insertFamilyData(null, "Persons", "1000", "prop", SecondFamilyData);
		hazCache.reset();
		assertEquals(0, hazCache.size());
	}
	@Test
	public void deleteTest() throws CacheException{
		Element e=new Element();
		String key="tagada";
		e.key=key;
		e.familyName="propssssssssss";
		e.familyData.put("Age", 12);
		e.familyData.put("name", 23);
		MetaInformation meta=new MetaInformation();
		meta.forElement(e);
		HazelCastCache  hazCache=new HazelCastCache();
		Map<String, byte[]> data=new HashMap<String,byte[]>();
		data.put("", new byte[10]);
		data.put("", new byte[20]);
		data.put("", new byte[30]);
		hazCache.insertFamilyData(meta, "table", key, "familyData", data);
		System.out.println(hazCache.getFamilyData(meta, "table", key, "familyData"));
		hazCache.delete(meta, "table", key);
		assertEquals(null,hazCache.getFamilyData(meta, "table", key, "familyData"));
	}

}
