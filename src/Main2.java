import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Main Main class for Calendar_IO
 * 
 * @author Wing Yiu Ng
 * @author Bobby White
 * @author Barryn Chun
 */
public class Main2 {

	private static String filename = "newcalendar.ics";
	//private static ArrayList<ArrayList<String>> events = new ArrayList<ArrayList<String>>();
	private static ICalendar cal = new ICalendar();
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

			System.out.println("There are now ( " + cal.size() + " ) events queued.");
			System.out.println("Events will be written to \"" + filename + "\".");
			System.out.println("(1) Change the name of your save file");
			System.out.println("(2) Add a new event");
			System.out.println("(3) List all events");
			System.out.println("(4) Load a file");
			System.out.println("(5) Exit");
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
				listEvents();
			} else if (choice == 4) {
				System.out.println("Loading file...");
				loadFile();
			} else if (choice == 5) { // exit
				System.out.println("Exiting Calendar_IO...");
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
		String inputS = (userInput.nextLine());
		if (inputS.isEmpty())
			return -2;
		int input;
		try {
			input = Integer.parseInt(inputS);
		} catch (Exception e) {
			return -1;
		}
		return input;
	}

	/**
	 * addEvent
	 * 
	 * adds a new event to the .ics file
	 */
	public static void addEvent() {
		KVList eventAttrib = new KVList();
		Event new_event = new Event();
		Date date = new Date();

		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMdd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");

		new_event = getEventDetails();

		// TODO Replace with editable values
		String description = new_event.getDescription();
		String comment = new_event.getComment();
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

		eventAttrib.add("DTSTART", date_start);
		eventAttrib.add("DTEND", date_end);
		eventAttrib.add("DTSTAMP", date_stamp);
		// UID is not needed unless you want to give the event a unique
		// identifier
		// details.add("UID:rhh4l2hdp1snqc7ego4lueh18c@google.com" + "\n");
		eventAttrib.add("CREATED", date_created);
		eventAttrib.add("DESCRIPTION", description);
		eventAttrib.add("LAST-MODIFIED", date_modified);
		eventAttrib.add("LOCATION", location);
		eventAttrib.add("GEO", latitude + ";" + longitude);
		eventAttrib.add("CLASS", classification);
		eventAttrib.add("SEQUENCE", "1");
		eventAttrib.add("STATUS", "CONFIRMED");
		eventAttrib.add("SUMMARY", summary);
		eventAttrib.add("TRANSP", "TRANSPARENT");

		cal.addEvent(eventAttrib);
	}

	public static void listEvents() {
		if (cal.size() > 0) {
			System.out.println("Listing all recorded events...");
			System.out.println(cal.printCal());
		} else {
			System.out.println("ERROR: There are no events!");
		}
	}

	/**
	 * TODO loads a .ics file
	 */
	private static void loadFile() {
		String filePath = "";

		System.out.print("New filename: ");
		filePath = userInput.nextLine();
		// if the given filename is valid
		if (filePath != "" || filePath != "\n") {
			ArrayList<String> fileContents = new ArrayList<String>();
			KVList pairs = new KVList();
			
			try {
				File source = new File(filePath);
				Scanner get = new Scanner(source);
				
				while (get.hasNextLine()) {
					fileContents.add(get.nextLine());
				}
				
				//if file begins with BEGIN:VCALENDAR and ends with END:VCALENDAR
				if (fileContents.get(0).equals("BEGIN:VCALENDAR") && fileContents.get(fileContents.size()-1).equals("END:VCALENDAR")) {
					//if file contains an equal number of BEGIN:VEVENT and END:VEVENT
					if (Collections.frequency(fileContents, "BEGIN:VEVENT") == Collections.frequency(fileContents, "END:VEVENT")) {
						
						//find the indexes
						ArrayList<Integer> event_begin = new ArrayList<Integer>();
						ArrayList<Integer> event_end = new ArrayList<Integer>();
						for (int x = 0; x < fileContents.size(); x++) {
							if (fileContents.get(x).equals("BEGIN:VEVENT")) {
								event_begin.add(x);
							} else if (fileContents.get(x).equals("END:VEVENT")) {
								event_end.add(x);
							}
						}
						
						/*
						KVList calAttrib = new KVList();
						//to first begin
						for (int iter1 = 1; iter1 < event_begin.get(0); iter1++) {
							calAttrib.add(fileContents.get(iter1));
							System.out.println(fileContents.get(iter1));
						}
						
						//while () {
							//
						//}
						*/
						
						
					} else {
						System.out.println("ERROR: File is not formatted correctly!");
					}
				} else {
					System.out.println("ERROR: File is not formatted correctly!");
				}
				
			} catch (FileNotFoundException e) {
				System.out.println("ERROR: Could not find the specified file!");
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println(pairs);
			System.out.println(pairs.getVal("METHOD"));
		} else {
			System.out.println("ERROR: Invalid filename!");
		}
	}

	/**
	 * Writes given text that prints calendar data to a file here.
	 */
	private static void saveFile() {
		ArrayList<String> buffer = new ArrayList<String>();
		String calendar_name = "TESTIMPORT";
		buffer.add(cal.toString());

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
		KVList details = new KVList();

		// the user is asked questions here
		System.out.print("Event Name: ");
		//new_event.setTitle(userInput.nextLine());
		details.add("SUMMARY", userInput.nextLine());
		
		System.out.print("Start Date (YYYYMMDD): ");
		String startDateInput = userInput.nextLine();
		while (!isValidDate(startDateInput)) {
			System.out.print("Incorrect Date! Please enter in the format YYYYMMDD: ");
			startDateInput = userInput.nextLine();
		}
		new_event.setDateStart(startDateInput);

		System.out.print("Start Time (HHMMSS): ");
		String startTimeInput = userInput.nextLine();
		while (!isValidTime(startTimeInput)) {
			System.out.print("Incorrect Time! Please enter in the format HHMMSS: ");
			startTimeInput = userInput.nextLine();
		}
		new_event.setTimeStart(startTimeInput);
		
		String dtstart = new_event.getDateStart() + "T" + new_event.getTimeStart();

		System.out.print("End Date (YYYYMMDD): ");
		String endDateInput = userInput.nextLine();
		while (!isValidDate(endDateInput)) {
			System.out.print("Incorrect Date! Please enter in the format YYYYMMDD: ");
			endDateInput = userInput.nextLine();
		}
		new_event.setDateEnd(endDateInput);

		System.out.print("End Time (HHMMSS): ");
		String endTimeInput = userInput.nextLine();
		while (!isValidTime(endTimeInput)) {
			System.out.print("Incorrect Time! Please enter in the format HHMMSS: ");
			endTimeInput = userInput.nextLine();
		}
		new_event.setTimeEnd(endTimeInput);
		
		String dtend = new_event.getDateEnd() + "T" + new_event.getTimeEnd();

		System.out.print("Location: ");
		new_event.setLocation(userInput.nextLine());

		System.out.println("Would you like to enter the latitude and longitude (y/n): ");
		String geo_choice = userInput.nextLine();
		while (geo_choice.charAt(0) != 'y' && geo_choice.charAt(0) != 'Y' && geo_choice.charAt(0) != 'n'
				&& geo_choice.charAt(0) != 'N') {
			System.out.println("Please enter y or n: ");
			geo_choice = userInput.nextLine();
		}
		if (geo_choice.charAt(0) == 'Y' || geo_choice.charAt(0) == 'y') {
			System.out.print("Latitude (-90 to 90): ");
			String lat_input = userInput.nextLine();
			while (!isFloat(lat_input) || Float.parseFloat(lat_input) > 90 || Float.parseFloat(lat_input) < -90) {
				System.out.print("Please enter a decimal between -90 and 90: ");
				lat_input = userInput.nextLine();
			}
			new_event.setLatitude(Float.parseFloat(lat_input));

			System.out.print("Longitude (-180 to 180): ");
			String lon_input = userInput.nextLine();
			while (!isFloat(lon_input) || Float.parseFloat(lon_input) > 180 || Float.parseFloat(lon_input) < -180) {
				System.out.print("Please enter a decimal between -180 and 180: ");
				lon_input = userInput.nextLine();
			}
			new_event.setLongitude(Float.parseFloat(lon_input));
		} else
			System.out.println("No geographic location entered");

		System.out.print("Classification (Default is PUBLIC) \n");
		System.out.print("(1)PUBLIC, (2)PRIVATE, (3)CONFIDENTIAL, (4)iana-token, (5)x-name: ");
		String[] classifications = { "PUBLIC", "PRIVATE", "CONFIDENTIAL", "iana-token", "x-name" };
		int class_choice = 0;
		class_choice = getChoice();
		while ((class_choice != -2) && (class_choice > 5 || class_choice < 1)) {
			System.out.print("(1)PUBLIC, (2)PRIVATE, (3)CONFIDENTIAL, (4)iana-token, (5)x-name: \n");
			System.out.print("Please enter a number from the list above: ");
			class_choice = getChoice();
		}
		if (class_choice == -2)
			new_event.setClassification("PUBLIC");
		else
			new_event.setClassification(classifications[class_choice - 1]);

		System.out.print("Description: ");
		new_event.setDescription(userInput.nextLine());
		
		// set comment to calculation from great circle distance don't know how 
		// that will work with the list. 
		// new_event.setComment(gcd calculation from list of events);
		
		
		// System.out.println(answers.toString());


		return new_event;
	}

	/**
	 * Checks if a the given string is a float
	 * 
	 * @param input
	 *            a given string
	 * @return
	 */
	public static boolean isFloat(String input) {
		try {
			Float.parseFloat(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Check if the date is a valid format
	 * 
	 * @param input
	 *            a given date string
	 * @return
	 */
	public static boolean isValidDate(String input) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		dateFormat.setLenient(false);
		try {
			dateFormat.parse(input.trim());
		} catch (ParseException pe) {
			return false;
		}
		return true;
	}

	/**
	 * Check if the time is a valid format.
	 * 
	 * @param input
	 *            a given time string
	 * @return
	 */
	public static boolean isValidTime(String input) {
		SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
		timeFormat.setLenient(false);
		try {
			timeFormat.parse(input.trim());
		} catch (ParseException pe) {
			return false;
		}
		return true;
	}
	// TODO add a menu where users can choose to add multiple events before
	// quitting.
}
