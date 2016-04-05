import java.util.ArrayList;

/**
 * ICalendar
 * 
 * @author Wing Yiu Ng
 * @author Bobby White
 * @author Barryn Chun
 */
public class ICalendar {

	private KVList attrib = new KVList();
	private ArrayList<Event> events = new ArrayList<Event>();
	
	public ICalendar() {
		attrib.add("VERSION", "2.0");
		attrib.add("PRODID", "-//Google Inc//Google Calendar 70.9054//EN");
		attrib.add("CALSCALE", "GREGORIAN");
		attrib.add("METHOD", "PUBLISH");
		attrib.add("X-WR-CALNAME", "TESTIMPORT");
		attrib.add("BEGIN", "VTIMEZONE");
		attrib.add("TZID", "Pacific/Honolulu");
		attrib.add("BEGIN", "STANDARD");
		attrib.add("TZOFFSETFROM", "-1000");
		attrib.add("TZOFFSETTO", "-1000");
		attrib.add("TZNAME", "HST");
		attrib.add("END", "STANDARD");
		attrib.add("END", "VTIMEZONE");
		attrib.add("X-WR-CALDESC", "");
	}
	
	public void setAttrib(KVList list) {
		attrib = list;
	}
	
	public void addEvent(KVList attrib) {
		events.add(new Event(attrib));
	}
	
	public void setName(String name) {
		attrib.setVal("X-WR-CALNAME", name);
	}
	
	public int size() {
		return events.size();
	}
	
	public String printCal() {
		String buffer = "";
		for (int i = 0; i < events.size(); i++) {
			buffer = buffer + "____________________________________________________" + "\n";
			buffer = buffer + events.get(i).printEvent();
		}
		buffer = buffer + "____________________________________________________" + "\n";
	
		return buffer;
	}
	
	public String toString() {
		String buffer = "BEGIN:VCALENDAR" + "\n";		
		buffer = buffer + attrib.toString();
		for (int i = 0; i < events.size(); i++) {
			buffer = buffer + events.get(i).toString();
		}
		buffer = buffer + "END:VCALENDAR";
		
		//System.out.println(buffer);
		return buffer;
	}
}
