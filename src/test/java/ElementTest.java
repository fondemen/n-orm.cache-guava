import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class ElementTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		String key="?1547:1258";
		Element e=new Element();
		e.key=key;
		e.delete();
	}

}
