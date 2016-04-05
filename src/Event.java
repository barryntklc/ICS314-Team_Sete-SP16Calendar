/**
 * Event
 * 
 * Represents an event object
 * 
 * @author Wing Yiu Ng
 * @author Bobby White
 * @author Barryn Chun
 */
public class Event {
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
}

//private String time_start;
	//private String time_end;
	//private String classification;
	//private float longitude, latitude;		// Geographic location
	//private String file_name;
	//private String comment;

//setTitle("");
		//setDescription("");
		//comment="";
		//date_created = getCurrentDate();
		//setClassification("PUBLIC");
		//setFileName(null);

/*
public Event (String titleI, String date_startI, String date_endI,
		String time_startI, String time_endI, String descriptionI, 
		String locationI, float latitudeI,
		float longitudeI, String file_nameI,String commentI) {
	setTitle(titleI);
	setDateStart(date_startI);
	setDateEnd(date_endI);
	setTimeStart(time_startI);
	setTimeEnd(time_endI);
	setDescription(descriptionI);
	setClassification("PUBLIC");
	setLocation(locationI);
	setLatitude(latitudeI);
	setLongitude(longitudeI);
	setFileName(file_nameI);
	setComment(commentI);
}


public Event (String titleI, String date_startI, String date_endI,
		String time_startI, String time_endI, String descriptionI, 
		String classificationI, String locationI, float latitudeI,
		float longitudeI, String file_nameI, String commentI) {
	setTitle(titleI);
	setDateStart(date_startI);
	setDateEnd(date_endI);
	setTimeStart(time_startI);
	setTimeEnd(time_endI);
	setDescription(descriptionI);
	setClassification(classificationI);
	setLocation(locationI);
	setLatitude(latitudeI);
	setLongitude(longitudeI);
	setFileName(file_nameI);
	setComment(commentI);
}
*/


/*
 * Setters
 */
/*
public void setTitle (String string) {
	//title = string;
	attrib.setVal("SUMMARY", string);
}
public void setDateStart (String string) {
	//date_start = string;
	attrib.setVal("DTSTART", string);
}
public void setDateEnd (String string) {
	//date_end = string;
	attrib.setVal("DTEND", string);
}
public void setTimeStart (String string) {
	time_start = string;
}
public void setTimeEnd (String string) {
	time_end = string;
}
public void setDescription (String string) {
	//description = string;
	attrib.setVal("DESCRIPTION", string);
}
public void setLocation (String string) {
	//location = string;
	attrib.setVal("LOCATION", string);
}
public void setLatitude (float lat) {
	latitude = lat;
}
public void setLongitude (float lon) {
	longitude = lon;
}
public void setClassification (String string) {
	classification = string;
}
public void setFileName (String string) {
	file_name = string;
}

public void setComment(String x) {
	comment=x;	
}
*/

/*
 * Getters
 */
/*public String getCurrentDate() {
	Calendar cal = Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDD'T'HHmmsszz");
}*/
/*
public String getTitle() {
	return attrib.getVal("TITLE");
}
public String getDateStart() {
	return attrib.getVal("DTSTART");
}
public String getDateEnd() {
	return attrib.getVal("DTEND");
}
public String getTimeStart() {
	return time_start;
}
public String getTimeEnd() {
	return time_end;
}
public String getDescription() {
	return attrib.getVal("SUMMARY");
}
public String getLocation() {
	return attrib.getVal("LOCATION");
}
public float getLatitude() {
	return latitude;
}
public float getLongitude() {
	return longitude;
}
public String getClassification() {
	return classification;
}
public String getFileName() {
	return file_name;
}
public String getComment() {
	return comment;
}
*/
