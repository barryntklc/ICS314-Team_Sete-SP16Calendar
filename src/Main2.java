import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;

/**
 * Main 
 * 
 * Main class for Calendar_IO
 * 
 * @author Wing Yiu Ng
 * @author Bobby White
 * @author Barryn Chun
 */
public class Main2 {

	private static String filename = "newcalendar.ics";
	private static ICalendar cal = new ICalendar();
	private static Scanner userInput = new Scanner(System.in);

	/**
	 * main
	 * 
	 * The main class. Runs the main menu.
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
	 * 
	 * Displays the main menu. Contains the following options:
	 * 1 - Lets the user change the name of their save file.
	 * 2 - Lets the user add a new event.
	 * 3 - Shows all events stored in the calendar.
	 * 4 - Loads a file.
	 * 5 - Exits the program, saves the current events to a calendar at save file path.
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
	 * Gets the user's menu choice.
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
		Date date = new Date();

		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMdd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");

		eventAttrib = getEventDetails();

		eventAttrib.add("DTSTAMP", dateFormat.format(date) + "T" + timeFormat.format(date));
		eventAttrib.add("CREATED", dateFormat.format(date) + "T" + timeFormat.format(date));
		eventAttrib.add("LAST-MODIFIED", dateFormat.format(date) + "T" + timeFormat.format(date));
		eventAttrib.add("SEQUENCE", "1");
		eventAttrib.add("STATUS", "CONFIRMED");
		eventAttrib.add("TRANSP", "TRANSPARENT");

		System.out.println(eventAttrib.getVal("SUMMARY"));
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

				// if file begins with BEGIN:VCALENDAR and ends with
				// END:VCALENDAR
				if (fileContents.get(0).equals("BEGIN:VCALENDAR")
						&& fileContents.get(fileContents.size() - 1).equals("END:VCALENDAR")) {
					int beginFreq = Collections.frequency(fileContents, "BEGIN:VEVENT");
					int endFreq = Collections.frequency(fileContents, "END:VEVENT");
					// if file contains an equal number of BEGIN:VEVENT and
					// END:VEVENT
					if (beginFreq == endFreq) {
						// if the file contains VEVENTS
						if (beginFreq > 0) {
							// find the indexes
							ArrayList<Integer> event_begin = new ArrayList<Integer>();
							ArrayList<Integer> event_end = new ArrayList<Integer>();
							for (int x = 0; x < fileContents.size(); x++) {
								if (fileContents.get(x).equals("BEGIN:VEVENT")) {
									event_begin.add(x);
								} else if (fileContents.get(x).equals("END:VEVENT")) {
									event_end.add(x);
								}
							}

							//read calendar attributes
							KVList calAttrib = new KVList();
							for (int iter1 = 1; iter1 < event_begin.get(0); iter1++) {
								calAttrib.add(fileContents.get(iter1).split(":"));
							}
							//TODO write calendar attributes to ICalendar object
							
							//read event attributes
							for (int iter2 = 0; iter2 < event_begin.size(); iter2++) {
								KVList eventAttrib = new KVList();
								int itera = event_begin.get(iter2) + 1;
								while (itera < event_end.get(iter2)) {
									eventAttrib.add(fileContents.get(itera).split(":"));
									itera++;
								}
								cal.addEvent(eventAttrib);
							}

						} else {
							//read calendar attributes
							KVList calAttrib = new KVList();
							for (int x = 0; x < fileContents.size() - 1; x++) {
								calAttrib.add(fileContents.get(x).split(":"));
							}
							//TODO write calendar attributes to ICalendar object
							System.out.println("NOTE: File did not contain any events.");
						}
					} else {
						System.out.println("ERROR: Does not have properly formatted VEVENT stubs!");
					}
				} else {
					System.out.println("ERROR: Does not have a properly formatted VCALENDAR stub!");
				}

				get.close();
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
	 * saveFile
	 * 
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
	 * getEventDetails
	 * 
	 * Queries the user for details on a created event, and returns an event
	 * 
	 * @return an Event of user input
	 */
	private static KVList getEventDetails() {
		// Event new_event = new Event();
		KVList details = new KVList();

		// SUMMARY
		System.out.print("Event Name: ");
		details.add("SUMMARY", userInput.nextLine());

		// DTSTART
		System.out.print("Start Date (YYYYMMDD): ");
		String startDateInput = userInput.nextLine();
		while (!isValidDate(startDateInput)) {
			System.out.print("Incorrect Date! Please enter in the format YYYYMMDD: ");
			startDateInput = userInput.nextLine();
		}
		System.out.print("Start Time (HHMMSS): ");
		String startTimeInput = userInput.nextLine();
		while (!isValidTime(startTimeInput)) {
			System.out.print("Incorrect Time! Please enter in the format HHMMSS: ");
			startTimeInput = userInput.nextLine();
		}
		String dtstart = startDateInput + "T" + startTimeInput;
		details.add("DTSTART", dtstart);

		// DTEND
		System.out.print("End Date (YYYYMMDD): ");
		String endDateInput = userInput.nextLine();
		while (!isValidDate(endDateInput)) {
			System.out.print("Incorrect Date! Please enter in the format YYYYMMDD: ");
			endDateInput = userInput.nextLine();
		}
		System.out.print("End Time (HHMMSS): ");
		String endTimeInput = userInput.nextLine();
		while (!isValidTime(endTimeInput)) {
			System.out.print("Incorrect Time! Please enter in the format HHMMSS: ");
			endTimeInput = userInput.nextLine();
		}
		String dtend = endDateInput + "T" + endTimeInput;
		details.add("DTEND", dtend);

		// LOCATION
		System.out.print("Location: ");
		details.add("LOCATION", userInput.nextLine());

		// GEO
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

			System.out.print("Longitude (-180 to 180): ");
			String lon_input = userInput.nextLine();
			while (!isFloat(lon_input) || Float.parseFloat(lon_input) > 180 || Float.parseFloat(lon_input) < -180) {
				System.out.print("Please enter a decimal between -180 and 180: ");
				lon_input = userInput.nextLine();
			}
			details.add("GEO", lat_input + ";" + lon_input);
		} else {
			System.out.println("No geographic location entered");
		}

		// CLASS
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
		if (class_choice == -2) {
			// new_event.setClassification("PUBLIC");
			details.add("CLASS", "PUBLIC");
		} else {
			// new_event.setClassification(classifications[class_choice - 1]);
			details.add("CLASS", classifications[class_choice - 1]);
		}

		// DESCRIPTION
		System.out.print("Description: ");
		// new_event.setDescription(userInput.nextLine());
		details.add("DESCRIPTION", userInput.nextLine());

		// set comment to calculation from great circle distance don't know how
		// that will work with the list.
		// new_event.setComment(gcd calculation from list of events);
		// System.out.println(answers.toString());
		// return new_event;
		return details;
	}

	/**
	 * isFloat
	 * 
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
	 * isValidDate
	 * 
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
	 * isValidTime
	 * 
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
}
