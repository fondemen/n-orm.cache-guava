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
	final GuavaCache gc=new GuavaCache();
	final int parallelGet=10;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}
	
	@Test
	public void parallelGetFamilyData() throws InterruptedException, ExecutionException, CacheException{
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
			};
		};
	
		Collection<Future<Map<String, byte[]>>> results=new LinkedList<Future<Map<String,byte[]>>>();
		ExecutorService es= new FixedThreadPool(parallelGet);
		for (int i = 0; i<parallelGet; i++){
			results.add(es.submit(r));
		}
		es.shutdown();

		for(Future<Map<String, byte[]>> f : results){
			//System.out.println(f.get());
			assertEquals(familyData, f.get());
		}
	}
	
	public void parallelInsertFamilyData(){
		final GuavaCache gc=new GuavaCache();
		final Map<String, byte[]> familyData=new HashMap<String, byte[]>();
		familyData.put("1", ConversionTools.convert(100));
		familyData.put("2", ConversionTools.convert(100));
		familyData.put("4", ConversionTools.convert(100));
		Runnable r =new Runnable(){

			@Override
			public void run() {
				try {
					gc.insertFamilyData(null, "Person", "290", "props", familyData);
				} catch (CacheException e) {
					e.printStackTrace();
					System.out.println("Error");
				}
			}
			
		};
	}

}

