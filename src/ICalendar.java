import java.util.ArrayList;
import java.util.Collections;

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

	public void sortEvents() {
		Collections.sort(events);
	}

	public int size() {
		return events.size();
	}

	// TODO add a menu where users can choose to add multiple events before
	// quitting.
	// to be placed where we will sorting function will be.
	/**
	 * calcGCD
	 * 
	 * Calculates the Great Circle Distance to the next event, if it exists
	 * unless GEO is blank, or the date difference is too great
	 */
	public void calcGCD() {
		// if there are at least two events in the list
		if (events.size() >= 2) {
			//for each event until the event before the last
			for (int i = 0; i < events.size() - 1; i++) {
				String date1 = events.get(i).getVal("DTEND");
				String date2 = events.get(i+1).getVal("DTSTART");
				
				// TODO: comparison seems to be not working
				if(date1.equals(date2)) { 
					String s1 = events.get(i).getVal("GEO");
					String s2 = events.get(i + 1).getVal("GEO");
					
					//if GEO is blank for either event
					if (s1.equals("") || s1.equals(null) || s2.equals("") || s2.equals(null)) {
						events.get(i).setVal("COMMENT", "Not enough information!");
						
					//else input comment normally
					} else {
						String geo1[] = events.get(i).getVal("GEO").split(";");
						double lat1 = Double.parseDouble(geo1[0]);
						double long1 = Double.parseDouble(geo1[1]);
						String geo2[] = events.get(i + 1).getVal("GEO").split(";");
						double lat2 = Double.parseDouble(geo2[0]);
						double long2 = Double.parseDouble(geo2[1]);
	
						float dist, km;
						double earthRadius = 3958.75; // miles (or 6371.0
														// kilometers)
						double dLat = Math.toRadians(lat2 - lat1);
						double dLng = Math.toRadians(long2 - long1);
						double sindLat = Math.sin(dLat / 2);
						double sindLng = Math.sin(dLng / 2);
						double a = Math.pow(sindLat, 2)
								+ Math.pow(sindLng, 2) * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
						double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
						dist = (float) (earthRadius * c);
						km = (float) (dist * 1.60934);
	
						String s = "The great circle distance to your next event is " + dist + " miles(or " + km + "km).";
						System.out.println(s);
						events.get(i).setVal("COMMENT", s);

					}
				}
			}
		} else {
			events.get(0).setVal("COMMENT","Not enough events for computing distance.");
		}
	}

	public String printCal() {
		String buffer = "";
		for (int i = 0; i < events.size(); i++) {
			buffer = buffer + "______________________________________________________________" + "\n";
			buffer = buffer + events.get(i).printEvent();
		}
		buffer = buffer + "______________________________________________________________" + "\n";

		return buffer;
	}

	public String toString() {
		String buffer = "BEGIN:VCALENDAR" + "\n";
		buffer = buffer + attrib.toString();
		for (int i = 0; i < events.size(); i++) {
			buffer = buffer + events.get(i).toString();
		}
		buffer = buffer + "END:VCALENDAR";

		return buffer;
	}
}
