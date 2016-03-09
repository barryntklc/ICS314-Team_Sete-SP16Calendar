import static org.junit.Assert.*;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTest {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTest.class.getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(EventTest.class);
		suite.addTestSuite(Main2Test.class);
		//$JUnit-END$
		return suite;
	}

}