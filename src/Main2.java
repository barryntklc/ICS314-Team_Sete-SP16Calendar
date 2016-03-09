import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Main
 * Main class for Calendar_IO
 * 
 * @author Wing Yiu Ng
 * @author Bobby White
 * @author Barryn Chun
 */
public class Main2 {

	private static String filename = "newcalendar.ics";
	private static ArrayList<ArrayList<String>> events = new ArrayList<ArrayList<String>>();
	private static Scanner userInput = new Scanner(System.in);
	
	/**
	 * main
	 * 
	 * The main class.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 1) {
			filename = args[0];
		}

		mainMenu();
	}

	/**
	 * mainMenu
	 */
	public static void mainMenu() {
		System.out.println("Welcome to Calendar_IO! (Team Sete)");

		boolean exit = false;

		while (!exit) {
			int choice = 0;

			System.out.println("There are now ( " + events.size() + " ) events queued.");
			System.out.println("Events will be written to \"" + filename + "\".");
			System.out.println("(1) Change the name of your save file");
			System.out.println("(2) Add a new event");
			System.out.println("(3) List all events");
			System.out.println("(4) Exit");
			System.out.print(": ");

			choice = getChoice();
			// throws an InputMismatchException when anything other than a
			// number is given

			System.out.println();
			if (choice == 1) { // change save file
				// change filename
				System.out.println("New filename: ");
				filename = userInput.nextLine();
				if (filename == "" || filename == "\n") {
					filename = "newcalendar.ics";
				}
				// System.out.println("Save file name changed to \"" +
				// "\".");
			} else if (choice == 2) { // add event
				System.out.println("Creating an event...");
				addEvent();
			} else if (choice == 3) { // list all
				System.out.println("Listing all recorded events...");
				listEvents();
			} else if (choice == 4) { // exit
				System.out.println("Exiting Calendar_IO");
				saveFile();
				exit = true;
			} else {
				System.out.println("ERROR: Invalid choice!");
			}
		}
	}

	/**
	 * getChoice
	 * 
	 * @return
	 */
	public static int getChoice() {
		int input = Integer.parseInt(userInput.nextLine());
		
		return input;
	}
	
	/**
	 * addEvent
	 * 
	 * adds a new event to the .ics file
	 */
	public static void addEvent() {
		ArrayList<String> details = new ArrayList<String>();
		Event new_event = new Event();
		Calendar cal = Calendar.getInstance();
		Date date = new Date();

		//System.out.println(cal.toString());

		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMdd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");

		new_event = getEventDetails();
		
		// TODO Replace with editable values
		String description = new_event.getDescription();
		String location = new_event.getLocation();
		String summary = new_event.getTitle();
		String date_start = new_event.getDateStart() + "T" + new_event.getTimeStart();
		String date_end = new_event.getDateEnd() + "T" + new_event.getTimeEnd();
		String date_stamp = dateFormat.format(date) + "T" + timeFormat.format(date);
		String date_created = dateFormat.format(date) + "T" + timeFormat.format(date);
		String date_modified = dateFormat.format(date) + "T" + timeFormat.format(date);
		String classification = new_event.getClassification();
		String latitude = Float.toString(new_event.getLatitude());
		String longitude = Float.toString(new_event.getLongitude());

		// TODO make DTSTART, DTEND customizable by the user
		details.add("BEGIN:VEVENT" + "\n");
		details.add("DTSTART:" + date_start + "\n");
		details.add("DTEND:" + date_end + "\n");
		details.add("DTSTAMP:" + date_stamp + "\n");
		// UID is not needed unless you want to give the event a unique
		// identifier
		// details.add("UID:rhh4l2hdp1snqc7ego4lueh18c@google.com" + "\n");
		details.add("CREATED:" + date_created + "\n");
		details.add("DESCRIPTION:" + description + "\n");
		details.add("LAST-MODIFIED:" + date_modified + "\n");
		details.add("LOCATION:" + location + "\n");
		details.add("GEO:" + latitude + ";" + longitude + "\n");
		details.add("CLASS:" + classification + "\n");
		details.add("SEQUENCE:1" + "\r\n");
		details.add("STATUS:CONFIRMED" + "\n");
		details.add("SUMMARY:" + summary + "\n");
		details.add("TRANSP:TRANSPARENT" + "\n");
		details.add("END:VEVENT" + "\n");

		events.add(details);
	}

	public static void listEvents() {
		for (int h = 0; h < events.size(); h++) {
			System.out.println("____________________________________________________");
			System.out.println("\"" + events.get(h).get(10).substring(8, events.get(h).get(10).length() - 1) + "\" - "
					+ events.get(h).get(4).substring(8, events.get(h).get(4).length() - 1));
			System.out.println(events.get(h).get(1).substring(8, events.get(h).get(1).length() - 1) + " ==> "
					+ events.get(h).get(2).substring(8, events.get(h).get(2).length() - 1) + "\n");
			System.out.println(events.get(h).get(7).substring(0, events.get(h).get(7).length() - 1));
			System.out.println(events.get(h).get(5).substring(0, events.get(h).get(5).length() - 1));
		}
		System.out.println("____________________________________________________" + "\n");
	}

	/**
	 * Writes given text that prints calendar data to a file here.
	 */
	private static void saveFile() {
		ArrayList<String> buffer = new ArrayList<String>();
		String calendar_name = "TESTIMPORT";

		buffer.add("BEGIN:VCALENDAR" + "\n");
		buffer.add("VERSION:2.0" + "\n");
		buffer.add("PRODID:-//Google Inc//Google Calendar 70.9054//EN" + "\n");
		buffer.add("CALSCALE:GREGORIAN" + "\n");
		buffer.add("METHOD:PUBLISH" + "\n");
		buffer.add("X-WR-CALNAME:" + calendar_name + "\n");
		buffer.add("BEGIN:VTIMEZONE" + "\n");
		buffer.add("TZID:Pacific/Honolulu" + "\n");
		buffer.add("BEGIN:STANDARD" + "\n");
		buffer.add("TZOFFSETFROM:-1000" + "\n");
		buffer.add("TZOFFSETTO:-1000" + "\n");
		buffer.add("TZNAME:HST" + "\n");
		buffer.add("END:STANDARD" + "\n");
		buffer.add("END:VTIMEZONE" + "\n");
		buffer.add("X-WR-CALDESC:" + "\n");

		for (int h = 0; h < events.size(); h++) {
			for (int i = 0; i < events.get(h).size(); i++) {
				buffer.add(events.get(h).get(i));
			}
		}
		buffer.add("END:VCALENDAR" + "\n");

		System.out.println("Buffer of output to be saved:");
		System.out.println(buffer);

		try {
			PrintWriter output = new PrintWriter(filename);
			for (int x = 0; x < buffer.size(); x++) {
				output.write(buffer.get(x));
			}
			output.close();
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: The file was not found.");
		}
	}

	/**
	 * Queries the user for details on a created event, and returns an event
	 * 
	 * @return an Event of user input
	 */
	private static Event getEventDetails() {
		Event new_event = new Event();

		// the user is asked questions here
		System.out.print("Event Name: ");
		new_event.setTitle(userInput.nextLine());
		
		System.out.print("Start Date (YYYYMMDD): ");
		new_event.setDateStart(userInput.nextLine());
		
		System.out.print("Start Time (HHMMSS): ");
		new_event.setTimeStart(userInput.nextLine());
		
		System.out.print("End Date (YYYYMMDD): ");
		new_event.setDateEnd(userInput.nextLine());
		
		System.out.print("End Time (HHMMSS): ");
		new_event.setTimeEnd(userInput.nextLine());
		
		System.out.print("Location: ");
		new_event.setLocation(userInput.nextLine());
		
		System.out.print("Latitude (-90 to 90): ");
		String lat_input = userInput.nextLine();
		while(Float.parseFloat(lat_input)>90 || Float.parseFloat(lat_input)<-90) {
			System.out.print("Please enter a number between -90 and 90: ");
			lat_input = userInput.nextLine();
		}
		new_event.setLatitude(Float.parseFloat(lat_input));
		
		System.out.print("Longiutude (-180 to 180): ");
		String lon_input = userInput.nextLine();
		while(Float.parseFloat(lon_input)>180 || Float.parseFloat(lon_input)<-180) {
			System.out.print("Please enter a number between -180 and 180: ");
			lon_input = userInput.nextLine();
		}
		new_event.setLongitude(Float.parseFloat(lon_input));
		
		System.out.print("Classification (PUBLIC is Default) \n");
		System.out.print("(1)PUBLIC, (2)PRIVATE, (3)CONFIDENTIAL, (4)iana-token, (5)x-name: ");
		String[] classifications = {"PUBLIC", "PRIVATE", "CONFIDENTIAL", "iana-token", "x-name"};
		int class_choice = 0;
		class_choice = getChoice();
		while(class_choice>5 || class_choice<1)  {
			System.out.print("(1)PUBLIC, (2)PRIVATE, (3)CONFIDENTIAL, (4)iana-token, (5)x-name: ");
			System.out.print("Please enter a number from the list above: ");
			class_choice = getChoice();
		}
		new_event.setClassification(classifications[class_choice-1]);
		
		System.out.print("Description: ");
		new_event.setDescription(userInput.nextLine());

		//System.out.println(answers.toString());
		
		return new_event;
	}
	
	/*
	 * Check if date and time are in correct format
	 */
	// TODO write function to check if date and time are valid
	// TODO add a menu where users can choose to add multiple events before
	// quitting.
}
