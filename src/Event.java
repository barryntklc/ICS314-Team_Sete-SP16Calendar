import java.util.Date;

/**
 * Event
 * 
 * Represents an event object
 * 
 * @author Wing Yiu Ng
 * @author Bobby White
 * @author Barryn Chun
 */
public class Event implements Comparable<Event> {
	private KVList attrib = new KVList();
	
	/**
	 * Constructor
	 */
	public Event() {
		attrib.setVal("SUMMARY", "");
		attrib.setVal("DESCRIPTION", "");
		attrib.setVal("COMMENT", "");
		attrib.setVal("CLASS", "PUBLIC");
	}
	
	public Event (KVList attrib) {
		this.attrib = attrib;
	}
	
	public String getVal(String key) {
		return attrib.getVal(key);
	}
	
	public void setVal(String key, String val) {
		attrib.setVal(key, val);
	}
	
	public void setVal(KVList attribs) {
		for (int n = 0; n < attribs.size(); n++) {
			attrib.setVal(attrib.getKey(n), attrib.getVal(n));
		}
	}
	
	public String printEvent() {
		String buffer = "";
		buffer = attrib.getVal("SUMMARY") + " ( " + attrib.getVal("DTSTART") + " - " + attrib.getVal("DTEND") + " )" + "\n";
		buffer = buffer + attrib.getVal("LOCATION") + "\n";
		buffer = buffer + "\n";
		buffer = buffer + attrib.getVal("DESCRIPTION") + "\n";
		buffer = buffer + "\n";
		
		return buffer;
	}

	public String toString() {
		String buffer = "BEGIN:VEVENT\n" + attrib.toString() + "END:VEVENT\n";
		return buffer;
	}

	public int compareTo(Event event) {
		// TODO Auto-generated method stub
		//return 0;
		String date1 = attrib.getVal("DTSTART");
		String date1_split[] = date1.split("T");
		int date1_Year = Integer.parseInt(date1_split[0].substring(0, 3));
		int date1_Month = Integer.parseInt(date1_split[0].substring(4, 5));
		int date1_Day = Integer.parseInt(date1_split[0].substring(6, 7));
		int date1_Hour = Integer.parseInt(date1_split[1].substring(0, 1));
		int date1_Minute = Integer.parseInt(date1_split[1].substring(2, 3));
		int date1_Second = Integer.parseInt(date1_split[1].substring(4, 5));
		@SuppressWarnings("deprecation")
		Date date1_Date = new Date(date1_Year, date1_Month, date1_Day, date1_Hour, date1_Minute, date1_Second);
		
		String date2 = event.getVal("DTSTART");
		String date2_split[] = date2.split("T");
		int date2_Year = Integer.parseInt(date2_split[0].substring(0, 3));
		int date2_Month = Integer.parseInt(date2_split[0].substring(4, 5));
		int date2_Day = Integer.parseInt(date2_split[0].substring(6, 7));
		int date2_Hour = Integer.parseInt(date2_split[1].substring(0, 1));
		int date2_Minute = Integer.parseInt(date2_split[1].substring(2, 3));
		int date2_Second = Integer.parseInt(date2_split[1].substring(4, 5));
		@SuppressWarnings("deprecation")
		Date date2_Date = new Date(date2_Year, date2_Month, date2_Day, date2_Hour, date2_Minute, date2_Second);
		
		return date1_Date.compareTo(date2_Date);
	}
}
