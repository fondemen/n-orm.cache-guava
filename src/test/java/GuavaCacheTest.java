import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.googlecode.n_orm.CacheTest;
import com.googlecode.n_orm.cache.read.CacheTester;
import com.googlecode.n_orm.cache.read.ICache;
import com.googlecode.n_orm.cache.read.guava.GuavaCache;


public class GuavaCacheTest extends CacheTester{

	@Override
	public ICache createCache() {
		// TODO Auto-generated method stub
		return new GuavaCache();
	}



}
