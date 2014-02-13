package com.googlecode.n_orm.cache.read.guava;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.googlecode.n_orm.cache.read.CacheException;
import com.googlecode.n_orm.cache.read.CacheTester;
import com.googlecode.n_orm.cache.read.ICache;
import com.googlecode.n_orm.cache.read.guava.GuavaCache;
import com.googlecode.n_orm.conversion.ConversionTools;


public class GuavaCacheTest extends CacheTester {

	@Override
	public ICache createCache() {
		return new GuavaCache(Long.MAX_VALUE, Long.MAX_VALUE, TimeUnit.MILLISECONDS);
	}

	@Test
	public void maxSizeDifferentVals() throws CacheException {
		int size = 3;
		GuavaCache sut = new GuavaCache(3, Long.MAX_VALUE, TimeUnit.MILLISECONDS);
		String table = "table";
		String family = "props";
		String key = "UYRYJKIG9IJ";
		
		Map<String, byte[]> familyData1 = new TreeMap<String, byte[]>();
		familyData1.put("prop", ConversionTools.convert("value1"));
		
		Map<String, byte[]> familyData2 = new TreeMap<String, byte[]>();
		familyData1.put("prop", ConversionTools.convert("value2"));
		
		Map<String, byte[]> familyData3 = new TreeMap<String, byte[]>();
		familyData1.put("prop", ConversionTools.convert("value3"));
		
		Map<String, byte[]> familyData4 = new TreeMap<String, byte[]>();
		familyData1.put("prop", ConversionTools.convert("value4"));
		
		sut.insertFamilyData(null, table, key, family, familyData1);
		
		assertEquals(familyData1, sut.getFamilyData(null, table, key, family));

		sut.insertFamilyData(null, table, key, family, familyData2);

		assertEquals(familyData2, sut.getFamilyData(null, table, key, family));

		sut.insertFamilyData(null, table, key, family, familyData3);

		assertEquals(familyData3, sut.getFamilyData(null, table, key, family));

		sut.insertFamilyData(null, table, key, family, familyData4);

		assertEquals(familyData4, sut.getFamilyData(null, table, key, family));
	}

	@Test
	public void maxSizeDifferentKeys() throws CacheException {
		int size = 3;
		GuavaCache sut = new GuavaCache(3, Long.MAX_VALUE, TimeUnit.MILLISECONDS);
		String table = "table";
		String family = "props";
		
		String key1 = "65RD45Y98YGF67U";
		Map<String, byte[]> familyData1 = new TreeMap<String, byte[]>();
		familyData1.put("prop", ConversionTools.convert("value1"));
		
		String key2 = "65RD45Y98YGF67V";
		Map<String, byte[]> familyData2 = new TreeMap<String, byte[]>();
		familyData1.put("prop", ConversionTools.convert("value2"));
		
		String key3 = "65RD45Y98YGF66W";
		Map<String, byte[]> familyData3 = new TreeMap<String, byte[]>();
		familyData1.put("prop", ConversionTools.convert("value3"));
		
		String key4 = "65RC45Y98YGF66W";
		Map<String, byte[]> familyData4 = new TreeMap<String, byte[]>();
		familyData1.put("prop", ConversionTools.convert("value4"));
		
		sut.insertFamilyData(null, table, key1, family, familyData1);
		
		assertEquals(familyData1, sut.getFamilyData(null, table, key1, family));
		assertNull(sut.getFamilyData(null, table, key2, family));
		assertNull(sut.getFamilyData(null, table, key3, family));
		assertNull(sut.getFamilyData(null, table, key4, family));

		sut.insertFamilyData(null, table, key2, family, familyData2);

		assertEquals(familyData1, sut.getFamilyData(null, table, key1, family));
		assertEquals(familyData2, sut.getFamilyData(null, table, key2, family));
		assertNull(sut.getFamilyData(null, table, key3, family));
		assertNull(sut.getFamilyData(null, table, key4, family));

		sut.insertFamilyData(null, table, key3, family, familyData3);

		assertEquals(familyData1, sut.getFamilyData(null, table, key1, family));
		assertEquals(familyData2, sut.getFamilyData(null, table, key2, family));
		assertEquals(familyData3, sut.getFamilyData(null, table, key3, family));
		assertNull(sut.getFamilyData(null, table, key4, family));

		sut.insertFamilyData(null, table, key4, family, familyData4);

		assertNull(sut.getFamilyData(null, table, key1, family));
		assertEquals(familyData2, sut.getFamilyData(null, table, key2, family));
		assertEquals(familyData3, sut.getFamilyData(null, table, key3, family));
		assertEquals(familyData4, sut.getFamilyData(null, table, key4, family));
	}

	@Test
	public void ttlOk() throws CacheException, InterruptedException {
		int ttl = 250;
		GuavaCache sut = new GuavaCache(Long.MAX_VALUE, ttl, TimeUnit.MILLISECONDS);
		String table = "table";
		String family = "props";
		String key = "YRFHGfduk";
		Map<String, byte[]> familyData = new TreeMap<String, byte[]>();
		familyData.put("prop", ConversionTools.convert("value1"));
		
		sut.insertFamilyData(null, table, key, family, familyData);
		assertEquals(familyData, sut.getFamilyData(null, table, key, family));
		
		Thread.sleep((long)(ttl*1.1));
		
		assertNull(sut.getFamilyData(null, table, key, family));
	}

}
