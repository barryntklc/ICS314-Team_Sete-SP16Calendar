import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Event {
	private String title;
	private String date_start;
	private String date_end;
	private String time_start;
	private String time_end;
	//private String date_stamp;
	private String description;
	private String classification;
	//private static String date_created;
	private String location;
	private float longitude, latitude;		// Geographic location
	private String file_name;
	
	
	/**
	 * Constructor
	 */
	public Event() {
		setTitle("");
		setDescription("");
		//date_created = getCurrentDate();
		setClassification("PUBLIC");
		setFileName(null);
	}
	
	public Event (String titleI, String date_startI, String date_endI,
			String time_startI, String time_endI, String descriptionI, 
			String locationI, float latitudeI,
			float longitudeI, String file_nameI) {
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
	}
	
	public Event (String titleI, String date_startI, String date_endI,
			String time_startI, String time_endI, String descriptionI, 
			String classificationI, String locationI, float latitudeI,
			float longitudeI, String file_nameI) {
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
	}
	
	
	/*
	 * Setters
	 */
	public void setTitle (String string) {
		title = string;
	}
	public void setDateStart (String string) {
		date_start = string;
	}
	public void setDateEnd (String string) {
		date_end = string;
	}
	public void setTimeStart (String string) {
		time_start = string;
	}
	public void setTimeEnd (String string) {
		time_end = string;
	}
	public void setDescription (String string) {
		description = string;
	}
	public void setLocation (String string) {
		location = string;
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
	
	/*
	 * Getters
	 */
	/*public String getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDD'T'HHmmsszz");
	}*/
	public String getTitle() {
		return title;
	}
	public String getDateStart() {
		return date_start;
	}
	public String getDateEnd() {
		return date_end;
	}
	public String getTimeStart() {
		return time_start;
	}
	public String getTimeEnd() {
		return time_end;
	}
	public String getDescription() {
		return description;
	}
	public String getLocation() {
		return location;
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
	
}
