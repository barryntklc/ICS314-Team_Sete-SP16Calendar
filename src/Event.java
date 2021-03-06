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
	private KVList preAttrib = new KVList();
	
	/**
	 * Constructor
	 */
	public Event() {
		preAttrib.setVal("BEGIN", "VTIMEZONE");
		preAttrib.setVal("TZID", "Pacific/Honolulu");
		preAttrib.setVal("BEGIN", "STANDARD");
		preAttrib.add("TZOFFSETFROM", "-1000");
		preAttrib.add("TZOFFSETTO", "-1000");
//		attrib.add("TZNAME", "HST");
		preAttrib.setVal("END", "STANDARD");
		preAttrib.setVal("END", "VTIMEZONE");
		
		attrib.setVal("SUMMARY", "");
		attrib.setVal("DESCRIPTION", "");
		attrib.setVal("COMMENT", "");
		attrib.setVal("CLASS", "PUBLIC");
		System.out.print("DISPLAYS PREATTRIB in constructor"+this.preAttrib.toString());
		
	}
	
	/**
	 * Constructor
	 * 
	 * given a list of attributes, creates a new Event
	 * 
	 * @param attrib - a list of attributes
	 */
	public Event (KVList attrib) {
		this.attrib = attrib;
	
	}
	
	/**
	 * getVal
	 * 
	 * @param key
	 * @return
	 */
	public String getVal(String key) {
		return attrib.getVal(key);
	}
	public void setPreAttrib(KVList gAttrib){
		preAttrib.add("BEGIN", "VTIMEZONE");
		preAttrib.add("TZID", "Pacific/Honolulu");
		preAttrib.add("BEGIN", "STANDARD");
		preAttrib.add("TZOFFSETFROM", "-1000");
		preAttrib.add("TZOFFSETTO", "-1000");
		//preAttrib.add("TZNAME", "HST");
		preAttrib.add("END", "STANDARD");
		preAttrib.add("END", "VTIMEZONE");
		this.preAttrib.setVal("TZID",gAttrib.getVal("TZID"));
		this.attrib.remove("TZID");

	}
	
	/**
	 * setVal
	 * 
	 * @param key
	 * @param val
	 */
	public void setVal(String key, String val) {
		attrib.setVal(key, val);
	}
	
	/**
	 * setVal
	 * 
	 * @param attribs
	 */
	public void setVal(KVList attribs) {
		for (int n = 0; n < attribs.size(); n++) {
			attrib.setVal(attrib.getKey(n), attrib.getVal(n));
		}
	}
	
	/**
	 * formattedDate
	 * 
	 * @param formattedDate
	 * @return
	 */
	public String translateDate (String formattedDate) {
		int date[] = splitDate(formattedDate);
		
		String AM_PM = "";
		if (date[3] >= 12) {
			AM_PM = "PM";
		} else {
			AM_PM = "AM";
		}
		return String.format("%02d", date[1]) + "/" + String.format("%02d", date[2]) + "/" + date[0] + " (" + date[3] + ":" + String.format("%02d", date[4]) + ":" + String.format("%02d", date[5]) + " " + AM_PM + ")";
	}
	
	/**
	 * translateMonth
	 * 
	 * @param monthInt
	 * @return
	 */
	public String translateMonth (int monthInt) {
		String[] monthName = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		if (monthInt < 1 || monthInt > 12) {
			return null;
		} else {
			return monthName[monthInt - 1];
		}
	}
	
	/**
	 * splitDate
	 * 
	 * Splits a .ics-formatted date String into separate values
	 */
	public int[] splitDate (String date) {
		int buffer[] = new int[6];
		String date_split[] = date.split("T");
		buffer[0] = Integer.parseInt(date_split[0].substring(0, 4)); //YEAR
		buffer[1] = Integer.parseInt(date_split[0].substring(4, 6)); //MONTH
		buffer[2] = Integer.parseInt(date_split[0].substring(6, 8)); //DAY
		buffer[3] = Integer.parseInt(date_split[1].substring(0, 2)); //HOUR
		buffer[4] = Integer.parseInt(date_split[1].substring(2, 4)); //MINUTE
		buffer[5] = Integer.parseInt(date_split[1].substring(4, 6)); //SECOND
		return buffer;
	}
	
	/**
	 * compareTo
	 * 
	 * Compares the starting date and time of this event with another, for the purpose of ordering it.
	 * 
	 * @return the compareTo value of two Date objects.
	 */
	public int compareTo(Event event) {
		int date1[] = splitDate(attrib.getVal("DTSTART"));
		@SuppressWarnings("deprecation")
		Date date1_Date = new Date(date1[0], date1[1], date1[2], date1[3], date1[4], date1[5]);
		
		int date2[] = splitDate(event.getVal("DTSTART"));
		@SuppressWarnings("deprecation")
		Date date2_Date = new Date(date2[0], date2[1], date2[2], date2[3], date2[4], date2[5]);
		
		return date1_Date.compareTo(date2_Date);
	}

	/**
	 * toStringReadable
	 * 
	 * @return a human-readable record of an Event.
	 */
	public String toStringReadable() {
		String buffer = "";
		buffer = "[ " + attrib.getVal("SUMMARY") + " ] (" + translateDate(attrib.getVal("DTSTART")) + " --> " + translateDate(attrib.getVal("DTEND")) + ")" + "\n";
		buffer = buffer + attrib.getVal("LOCATION") + "\n";
		buffer = buffer + "\n";
		buffer = buffer + attrib.getVal("DESCRIPTION") + "\n";
		buffer = buffer + "\n";
		buffer = buffer + attrib.getVal("COMMENT") + "\n";
		buffer = buffer + "\n";
		
		return buffer;
	}

	/**
	 * toString
	 * 
	 * @return the Event's attributes as formatted data.
	 */
	public String toString() {
		setPreAttrib(this.attrib);
		String buffer = preAttrib.toString() + "BEGIN:VEVENT\n" + attrib.toString() + "END:VEVENT\n";
		return buffer;
	}
}
