import static org.junit.Assert.*;

import org.junit.Test;
import junit.framework.TestCase;


public class Main2Test extends TestCase{

	public void testIsFloat() {
		assertFalse("Not Float Input\n", Main2.isFloat("sushi"));
		assertFalse("Not Float Input\n", Main2.isFloat("34k432"));
		assertTrue("It's a Float\n", Main2.isFloat("2343.45"));
	}
	
	public void testIsValidDate() {
		assertFalse("Invalid Date!\n", Main2.isValidDate("sushi"));
		assertFalse("Invalid Date!\n", Main2.isValidDate("20161459"));
		assertTrue("Date is Valid!\n", Main2.isValidDate("20160229"));
	}
	
	public void testIsValidTime() {
		assertFalse("Invalid Time!\n", Main2.isValidTime("sushi"));
		assertFalse("Invalid Time!\n", Main2.isValidTime("157900"));
		assertFalse("Invalid Time!\n", Main2.isValidTime("15000078"));
		assertTrue("Time is Valid!\n", Main2.isValidTime("135959"));
	}
}
