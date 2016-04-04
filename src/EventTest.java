import static org.junit.Assert.*;

import org.junit.Test;
import junit.framework.TestCase;


public class EventTest extends TestCase {

	public void testEvent1() {
		Event event1 = new Event();
		assertEquals("", event1.getTitle());
		assertEquals(null, event1.getDateStart());
		assertEquals(null, event1.getDateEnd());
		assertEquals(null, event1.getTimeStart());
		assertEquals(null, event1.getTimeEnd());
		assertEquals("", event1.getDescription());
		assertEquals("PUBLIC", event1.getClassification());
		assertEquals(0.0f, event1.getLatitude());
		assertEquals(0.0f, event1.getLongitude());
		assertEquals(null, event1.getFileName());
	}
	
	public void testEvent2() {
		Event event1 = new Event("Testing", "20160307", "20160309", "120000", "031500",
				"Test Case", "POST", -49.50f, 93.00f, "New_Event", "");
		assertEquals("Testing", event1.getTitle());
		assertEquals("20160307", event1.getDateStart());
		assertEquals("20160309", event1.getDateEnd());
		assertEquals("120000", event1.getTimeStart());
		assertEquals("031500", event1.getTimeEnd());
		assertEquals("Test Case", event1.getDescription());
		assertEquals("PUBLIC", event1.getClassification());
		assertEquals("POST", event1.getLocation());
		assertEquals(-49.50f, event1.getLatitude());
		assertEquals(93.00f, event1.getLongitude());
		assertEquals("New_Event", event1.getFileName());
		assertEquals("Test Case", event1.getComment());
	}
	
	public void testEvent3() {
		Event event1 = new Event("Testing", "20160307", "20160309", "120000", "031500",
				"Test Case", "PRIVATE", "POST", -49.50f, 93.00f, "New_Event","Test Case Comment");
		assertEquals("Testing", event1.getTitle());
		assertEquals("20160307", event1.getDateStart());
		assertEquals("20160309", event1.getDateEnd());
		assertEquals("120000", event1.getTimeStart());
		assertEquals("031500", event1.getTimeEnd());
		assertEquals("Test Case", event1.getDescription());
		assertEquals("PRIVATE", event1.getClassification());
		assertEquals("POST", event1.getLocation());
		assertEquals(-49.50f, event1.getLatitude());
		assertEquals(93.00f, event1.getLongitude());
		assertEquals("New_Event", event1.getFileName());
		assertEquals("Test Case Comment", event1.getComment());
	}
	
}
