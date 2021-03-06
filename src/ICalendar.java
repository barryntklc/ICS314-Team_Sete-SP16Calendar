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

	/**
	 * ICalendar
	 */
	public ICalendar() {
		attrib.add("VERSION", "2.0");
		attrib.add("PRODID", "-//Google Inc//Google Calendar 70.9054//EN");
		attrib.add("CALSCALE", "GREGORIAN");
		attrib.add("METHOD", "PUBLISH");
		attrib.add("X-WR-CALNAME", "TESTIMPORT");
//		attrib.add("BEGIN", "VTIMEZONE");
//		attrib.add("TZID", "Pacific/Honolulu");
//		attrib.add("BEGIN", "STANDARD");
//		attrib.add("TZOFFSETFROM", "-1000");
//		attrib.add("TZOFFSETTO", "-1000");
//		attrib.add("TZNAME", "HST");
//		attrib.add("END", "STANDARD");
//		attrib.add("END", "VTIMEZONE");
		attrib.add("X-WR-CALDESC", "");
	}

	/**
	 * setAttrib
	 * @param list
	 */
	public void setAttrib(KVList list) {
		attrib = list;
	}

	/**
	 * addEvent
	 * @param attrib
	 */
	public void addEvent(KVList attrib) {
		events.add(new Event(attrib));
	}

	/**
	 * setName
	 * 
	 * @param name - a name for the calendar
	 */
	public void setName(String name) {
		attrib.setVal("X-WR-CALNAME", name);
	}

	/**
	 * sortEvents
	 * 
	 * Sorts the array of events stored.
	 */
	public void sortEvents() {
		Collections.sort(events);
	}

	/**
	 * size
	 * 
	 * @return the size of the array of events stored.
	 */
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
				
				if(withinThreshold(splitDate(date1), splitDate(date2))) { 
					String s1 = events.get(i).getVal("GEO");
					String s2 = events.get(i + 1).getVal("GEO");
					
					System.out.println("S1:" + s1); //DEBUG
					System.out.println("S2:" + s2); //DEBUG
					
					//if GEO is blank for either event
					if (s1 == null || s1.equals("") || s2 == null || s2.equals("")) {
						events.get(i).setVal("DESCRIPTION", events.get(i).getVal("DESCRIPTION") + "\tNot enough information!");
						
					//else input comment normally
					} else {
						String geo1[] = events.get(i).getVal("GEO").split(";");
						float lat1 = Float.parseFloat(geo1[0]);
						double long1 = Float.parseFloat(geo1[1]);
						String geo2[] = events.get(i + 1).getVal("GEO").split(";");
						double lat2 = Float.parseFloat(geo2[0]);
						double long2 = Float.parseFloat(geo2[1]);
	
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
						events.get(i).setVal("DESCRIPTION", events.get(i).getVal("DESCRIPTION") + '\t' + s);
					}
				} else {
					events.get(i).setVal("DESCRIPTION", events.get(i).getVal("DESCRIPTION") + "\tNot the same date!");
				}
			}
		} else {
			events.get(0).setVal("DESCRIPTION", events.get(0).getVal("DESCRIPTION") + "\tNot enough events for computing distance.");
		}
	}
	
	/**
	 * splitDate
	 * 
	 * @param date a given date string
	 * @return the YYYYMMDDTHHMMSS string in an int array
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
	 * withinThreshold
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public boolean withinThreshold (int[] date1, int[] date2) {
		
		if (date1[0] == date2[0] && date1[1] == date2[1] && date1[2] == date2[2]) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * toStringReadable
	 * 
	 * @return a human-readable list of Events
	 */
	public String toStringReadable() {
		String buffer = "";
		for (int i = 0; i < events.size(); i++) {
			buffer = buffer + "______________________________________________________________" + "\n";
			buffer = buffer + events.get(i).toStringReadable();
		}
		buffer = buffer + "______________________________________________________________" + "\n";

		return buffer;
	}

	/**
	 * toString
	 * 
	 * @return an ics-formatted representation of this ICalendar
	 */
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
