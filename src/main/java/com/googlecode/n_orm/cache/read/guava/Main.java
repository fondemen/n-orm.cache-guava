package com.googlecode.n_orm.cache.read.guava;

import java.util.HashMap;
import java.util.Map;

import com.googlecode.n_orm.conversion.ConversionTools;

public class Main {

	/**
	 * @param args
	 * @throws CacheException 
	 */
	public static void main(String[] args) throws CacheException {
		 GuavaCache gc=new GuavaCache();
		 Map<String, byte[]> familyData=new HashMap<String, byte[]>();
		familyData.put("FirstName", ConversionTools.convert(10));
		familyData.put("SecondName", ConversionTools.convert("20"));
		familyData.put("First", ConversionTools.convert(100));
		familyData.put("Second", ConversionTools.convert("20000"));
		gc.insertFamilyData(null, "Person", "300", "Props", familyData);
		System.out.println(gc.getFamilyData(null, "Person", "300", "Props"));
		System.out.println(gc.getFamilyData(null, "Person", "300", "Props"));
		System.out.println("++++++++++++++++" + gc.size());
		
		

	}

}
