package com.googlecode.n_orm.cache.read.guava;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.googlecode.n_orm.PropertyManagement;
import com.googlecode.n_orm.StoreSelector;
import com.googlecode.n_orm.cache.read.CacheException;
import com.googlecode.n_orm.conversion.ConversionTools;
import com.googlecode.n_orm.memory.Memory;
import com.googlecode.n_orm.storeapi.SimpleStoreWrapper;

public class GuavaCacheStoreTest {

	protected static GuavaCacheStore sut;
	protected static GuavaCache cache;
	
	@BeforeClass
	public static void getSut() {
		sut = (GuavaCacheStore)StoreSelector.getInstance().getStoreFor(Element.class);
		assertNotNull(sut);
		assertEquals(GuavaCache.class, sut.getCache().getClass());
		assertEquals(Memory.INSTANCE, ((SimpleStoreWrapper)sut.getDeepActualStore()).getStore());
		cache = (GuavaCache) sut.getCache();
	}
	
	@After
	public void cleanup() throws CacheException {
		cache.reset();
		Memory.INSTANCE.reset();
	}
	
	@Before
	public void enableCache() {
		GuavaCacheStore.setEnabledForCurrentThread(true);
	}
	
	@Test
	public void properties() {
		assertEquals(25000, sut.getTtl());
		assertEquals(TimeUnit.MICROSECONDS, sut.getUnit());
		assertEquals(1024, sut.getMaxSize());
		assertFalse(sut.isEnabledByDefault());
	}
	
	@Test
	public void disabledCache() throws CacheException {

		GuavaCacheStore.setEnabledForCurrentThread(false);
		
		Element e = new Element();
		e.key = "hkhjljk";
		e.value = "hyfegbkzeio";
		
		e.store();
		
		Element f = new Element();
		f.key = "hkhjljk";
		
		e.activate();
		
		assertNull(cache.getFamilyData(null, e.getTable(), e.getIdentifier(), PropertyManagement.PROPERTY_COLUMNFAMILY_NAME));
	}
	
	@Test
	public void enabledCache() throws CacheException {
		GuavaCacheStore.setEnabledForCurrentThread(true);
		Element e = new Element();
		e.key = "hkhjljk";
		e.value = "hyfegbkzeio";
		
		e.store();
		
		Element f = new Element();
		f.key = "hkhjljk";
		
		f.activate();
		assertEquals(e.value, f.value);
		
		Map<String,byte[]> cached = cache.getFamilyData(null, e.getTable(), e.getIdentifier(), PropertyManagement.PROPERTY_COLUMNFAMILY_NAME);
		assertEquals(1, cached.size());
		assertArrayEquals(ConversionTools.convert(e.value), cached.get("value"));
	}
}
