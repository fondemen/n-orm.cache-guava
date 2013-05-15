package com.googlecode.n_orm.cache.read.guava;

import java.util.HashMap;
import java.util.Map;

import com.googlecode.n_orm.conversion.ConversionTools;

public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 * @throws CacheException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws CacheException, InterruptedException {
		GuavaCache gc=new GuavaCache();
		Map<String, byte[]> familyData=new HashMap<String, byte[]>();
		byte[] Dupond = ConversionTools.convert("Dupont");
		byte[] Jean = null;
		familyData.put("nom", Dupond);
		familyData.put("Prenom", Jean);
		gc.insertFamilyData(null, "Person", "290", "props", familyData);
		System.out.println(gc.getFamilyData(null, "Person", "290", "props"));
		System.out.println(gc.size());
		gc.reset();
		System.out.println(gc.getFamilyData(null, "Person", "290", "props"));
	}

}
