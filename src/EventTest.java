import static org.junit.Assert.*;

import org.junit.Test;
import junit.framework.TestCase;


public class EventTest extends TestCase {

	public void testEvent1() {
		//tests Event()
		
		Event event1 = new Event();
		assertEquals("", event1.getVal("SUMMARY"));
		assertEquals("", event1.getVal("DESCRIPTION"));
		assertEquals("", event1.getVal("COMMENT"));
		assertEquals("PUBLIC", event1.getVal("CLASS"));
	}
	
	
	public void testEvent2() {
		
		//tests Event(KVList attrib)
		
		/*
		 * 
		 * Event event1 = new Event("Testing", "20160307", "20160309", "120000", "031500",
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
		*/
	}
	
/*
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
	*/
	
}
