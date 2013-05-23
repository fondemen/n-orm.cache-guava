import static org.junit.Assert.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.junit.BeforeClass;
import org.junit.Test;

import com.googlecode.n_orm.cache.read.guava.CacheException;
import com.googlecode.n_orm.cache.read.guava.GuavaCache;
import com.googlecode.n_orm.cache.write.FixedThreadPool;
import com.googlecode.n_orm.conversion.ConversionTools;


public class CacheTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}
	
	@Test
	public void parallelGetFamilyData() throws InterruptedException, ExecutionException, CacheException{
		final GuavaCache gc=new GuavaCache();
		final int parallelGet=10;
		Map<String, byte[]> familyData=new HashMap<String, byte[]>();
		byte[] Dupond = ConversionTools.convert("Dupont");
		byte[] Jean = null;
		byte[] age= ConversionTools.convert("10");
		familyData.put("nom", Dupond);
		familyData.put("Prenom", Jean);
		familyData.put("age", age);
		gc.insertFamilyData(null, "Person", "290", "props", familyData);
		
		Callable<Map<String, byte[]> > r=new Callable<Map<String, byte[]> >() {

			@Override
			public Map<String, byte[]> call() throws Exception {
				return gc.getFamilyData(null, "Person", "290", "props");
			}
		};
	
		Collection<Future<Map<String, byte[]>>> results=new LinkedList<Future<Map<String,byte[]>>>();
		ExecutorService es= new FixedThreadPool(parallelGet);
		for (int i = 0; i<parallelGet; i++){
			results.add(es.submit(r));
		}
		es.shutdown();

		for(Future<Map<String, byte[]>> f : results){
			assertEquals(familyData, f.get());
		}
		assertEquals(1,gc.size());
	}
	
	@Test
	public void parallelFamilyData() throws CacheException, InterruptedException, ExecutionException{
		
		final GuavaCache gc=new GuavaCache();
		final int parallelThread = 10;
		
		final Map<String, byte[]> familyData=new HashMap<String, byte[]>();
		final Map<String, byte[]> animalData=new HashMap<String, byte[]>();
		
		familyData.put("FirstName", ConversionTools.convert(10));
		familyData.put("SecondName", ConversionTools.convert("20"));
		
		gc.insertFamilyData(null, "Person", "300", "Props", familyData);
		
		animalData.put("Age", ConversionTools.convert("20"));
		animalData.put("Name", ConversionTools.convert("Lion"));
		
		gc.insertFamilyData(null, "Animal", "235", "Carnivore", animalData);
		
		Callable<Map<String, byte[]>> r = new Callable<Map<String, byte[]>>() {
			@Override
			public Map<String, byte[]> call() throws Exception {
					return gc.getFamilyData(null, "Person", "300", "props");
			}
		};
		
		Callable<Map<String, byte[]>> rr = new Callable<Map<String, byte[]>>() {
			@Override
			public Map<String, byte[]> call() throws Exception {
					return gc.getFamilyData(null, "Animal", "235", "Carnivore");
					
			}
		};
		Collection<Future<Map<String, byte[]>>> results=new LinkedList<Future<Map<String,byte[]>>>();
		ExecutorService es= new FixedThreadPool(parallelThread);
		for (int i = 0; i<parallelThread; i++){
			if(i %2==0){
			   results.add(es.submit(r));
			   }
			else{
				results.add(es.submit(rr));
			}
		}
		es.shutdown();
		
		for(Future<Map<String, byte[]>> f : results){
			System.out.println("++++++++++++" + f.get());
			}
		
	}

}

