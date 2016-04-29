

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Scanner;
import java.util.TimeZone;
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
		/*
		System.out.println(new Event().translateMonth(0));
		System.out.println(new Event().translateMonth(1));
		System.out.println(new Event().translateMonth(2));
		System.out.println(new Event().translateMonth(3));
		System.out.println(new Event().translateMonth(4));
		System.out.println(new Event().translateMonth(5));
		System.out.println(new Event().translateMonth(6));
		System.out.println(new Event().translateMonth(7));
		System.out.println(new Event().translateMonth(8));
		System.out.println(new Event().translateMonth(9));
		System.out.println(new Event().translateMonth(10));
		System.out.println(new Event().translateMonth(11));
		System.out.println(new Event().translateMonth(12));
		System.out.println(new Event().translateMonth(13));
		*/
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
			System.out.println(cal.toStringReadable());
		} else {
			System.out.println("ERROR: There are no events!");
		}
	}

	/**
	 * TODO loads a .ics file
	 */
	private static void loadFile() {
		String filePath = "";

		System.out.print("Load file: ");
		filePath = userInput.nextLine();
		// if the given filename is valid
		if (filePath != "" || filePath != "\n") {
			ArrayList<String> fileContents = new ArrayList<String>();
			//KVList pairs = new KVList();

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

							//sort events
							cal.sortEvents();
							cal.calcGCD();
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

			//System.out.println(pairs);
			//System.out.println(pairs.getVal("METHOD"));
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
		buffer.add(cal.toString());

		//System.out.println("Buffer of output to be saved:");
		//System.out.println(buffer);

		try {
			System.out.println("Saving events to file.");
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
		KVList  details = new KVList();

		// SUMMARY
		System.out.print("Event Name: ");
		details.add("SUMMARY", userInput.nextLine());

//		// DTSTART
//		System.out.print("Start Date (YYYYMMDD): ");
//		String startDateInput = userInput.nextLine();
//		while (!isValidDate(startDateInput)) {
//			System.out.print("Incorrect Date! Please enter in the format YYYYMMDD: ");
//			startDateInput = userInput.nextLine();
//		}
//		
		System.out.print("Start Time (HHMMSS): ");
		String startTimeInput = userInput.nextLine();
		while (!isValidTime(startTimeInput)) {
			System.out.print("Incorrect Time! Please enter in the format HHMMSS: ");
			startTimeInput = userInput.nextLine();
		}
		
//		String dtstart = startDateInput + "T" + startTimeInput;
//		details.add("DTSTART", dtstart);

//		// DTEND
//		System.out.print("End Date (YYYYMMDD): ");
//		String endDateInput = userInput.nextLine();
//		while (!isValidDate(endDateInput)) {
//			System.out.print("Incorrect Date! Please enter in the format YYYYMMDD: ");
//			endDateInput = userInput.nextLine();
//		}
		System.out.print("End Time (HHMMSS): ");
		String endTimeInput = userInput.nextLine();
		while (!isValidTime(endTimeInput)) {
			System.out.print("Incorrect Time! Please enter in the format HHMMSS: ");
			endTimeInput = userInput.nextLine();
		}
//		String dtend = endDateInput + "T" + endTimeInput;
//		details.add("DTEND", dtend);
		
		//Timezone Selection
		
		System.out.print("Would you like to choose a timezone (y/n):");
		
		String tzidAnswer = userInput.nextLine();
		TimeZone timezone = TimeZone.getDefault();
		if(tzidAnswer.charAt(0)=='Y'|| tzidAnswer.charAt(0)=='y'){
			while(!isValidTZ(tzidAnswer)){
	
					displayTimeZone();
					System.out.print("Please select a timezone (enter signed number only)\n: ");
					tzidAnswer = userInput.nextLine();
									
			}
			String tzidFrontForm = tzidAnswer.substring(0,tzidAnswer.length()-2);
			String tzidBackForm = tzidAnswer.substring(tzidAnswer.length()-2);
			
			timezone.setID("GMT"+tzidFrontForm+":"+tzidBackForm);
			//System.out.println("Display name is : " + timezone.getDisplayName());
			details.add("TZID",timezone.getID());
		}
		
		

		
		//Renovating Date start and date end after time zone entry. 
		
		
		
		// DTSTART
		System.out.print("Start Date (YYYYMMDD): ");
		String startDateInput = userInput.nextLine();
		while (!isValidDate(startDateInput)) {
			System.out.print("Incorrect Date! Please enter in the format YYYYMMDD: ");
			startDateInput = userInput.nextLine();
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
			System.out.println("No geographic location entered.");
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

	private static void displayTimeZone() {
	
		System.out.println("=======Timezone (GMT) ======");
		String format = "%-76s%s%n";
		String format2 = "%-70s%n";

		String prefix1 = "(GMT-1000) Hawaii";
		String prefix2 = "(GMT+0300) Nairobi";
		System.out.printf(format, prefix1, prefix2);

		String prefix3 = "(GMT-0800) Alaska";
		String prefix4 = "(GMT+0300) Baghdad";
		System.out.printf(format, prefix3, prefix4);

		String prefix5 = "(GMT-0700) Pacific time (US and Canada)";
		String prefix6 = "(GMT+0400) Moscow, St. Petersburg, Volgograd";
		System.out.printf(format, prefix5, prefix6);

		String prefix7 = "(GMT-0700) Tijuana, Baja California";
		String prefix8 = "(GMT+0400) Abu Dhabi, Muscat";
		System.out.printf(format, prefix7, prefix8);

		String prefix9 = "(GMT-0700) Arizona";
		String prefix10 = "(GMT+0400) Port Louis";
		System.out.printf(format, prefix9, prefix10);

		String prefix11 = "(GMT-0600) Mountain Time (US and Canada)";
		String prefix12 = "(GMT+0400) Yerevan";
		System.out.printf(format, prefix11, prefix12);

		String prefix13 = "(GMT-0600) Central America";
		String prefix14 = "(GMT+0400) Tbilisi";
		System.out.printf(format, prefix13, prefix14);

		String prefix15 = "(GMT-0600) Saskatchewan";
		String prefix16 = "(GMT+0430) Tehran";
		System.out.printf(format, prefix15, prefix16);

		String prefix17 = "(GMT-0500) Central Time (US and Canada)";
		String prefix18 = "(GMT+0430) Kabul";
		System.out.printf(format, prefix17, prefix18);

		String prefix19 = "(GMT-0500) Bogota, Lima, Quito";
		String prefix20 = "(GMT+0500) Baku";
		System.out.printf(format, prefix19, prefix20);

		String prefix21 = "(GMT-0500) Mexico City";
		String prefix22 = "(GMT+0500) Islamabad, Karachi";
		System.out.printf(format, prefix21, prefix22);

		String prefix23 = "(GMT-0430) Caracas";
		String prefix24 = "(GMT+0500) Tashkent";
		System.out.printf(format, prefix23, prefix24);

		String prefix25 = "(GMT-0400) Eastern Time (US and Canada)";
		String prefix26 = "(GMT+0530) Chennai, Kolkata, Mumbai, New Delhi";
		System.out.printf(format, prefix25, prefix26);

		String prefix27 = "(GMT-0400) Manaus";
		String prefix28 = "(GMT+0530) Sri Jayawardenepura";
		System.out.printf(format, prefix27, prefix28);

		String prefix29 = "(GMT-0400) Georgetown";
		String prefix30 = "(GMT+0545) Kathmandu";
		System.out.printf(format, prefix29, prefix30);

		String prefix31 = "(GMT-0400) Santiago";
		String prefix32 = "(GMT+0600) Ekaterinburg";
		System.out.printf(format, prefix31, prefix32);

		String prefix33 = "(GMT-0400) La Paz";
		String prefix34 = "(GMT+0600) Astana, Dhaka";
		System.out.printf(format, prefix33, prefix34);

		String prefix35 = "(GMT-0400) Asuncion";
		String prefix36 = "(GMT+0630) Rangoon";
		System.out.printf(format, prefix35, prefix36);

		String prefix37 = "(GMT-0400) Indiana (East)";
		String prefix38 = "(GMT+0700) Novosibirsk";
		System.out.printf(format, prefix37, prefix38);

		String prefix39 = "(GMT-0300) Brasilla";
		String prefix40 = "(GMT+0700) Bangkok, Hanoi, Jakarta";
		System.out.printf(format, prefix39, prefix40);

		String prefix41 = "(GMT-0300) Montevideo";
		String prefix42 = "(GMT+0800) Krasnoyarsk";
		System.out.printf(format, prefix41, prefix42);

		String prefix43 = "(GMT-0300) Atlantic Time (Canada)";
		String prefix44 = "(GMT+0800) Perth";
		System.out.printf(format, prefix43, prefix44);

		String prefix45 = "(GMT-0300) Buenos Aires";
		String prefix46 = "(GMT+0800) Taipei";
		System.out.printf(format, prefix45, prefix46);

		String prefix47 = "(GMT-0300) Cayenne";
		String prefix48 = "(GMT+0800) Beijing, Chongqing, Hong Kong, Urumqi";
		System.out.printf(format, prefix47, prefix48);

		String prefix49 = "(GMT-0230) Newfoundland";
		String prefix50 = "(GMT+0800) Kuala Lumpur, Singapore";
		System.out.printf(format, prefix49, prefix50);

		String prefix51 = "(GMT-0200) Mid-Atlantic";
		String prefix52 = "(GMT+0800) Ulaan Bataar";
		System.out.printf(format, prefix51, prefix52);

		String prefix53 = "(GMT-0200) Greenland";
		String prefix54 = "(GMT+0900) Irkutsk";
		System.out.printf(format, prefix53, prefix54);

		String prefix55 = "(GMT-0100) Cape Verde Is.";
		String prefix56 = "(GMT+0900) Osaka, Sapporo, Tokyo";
		System.out.printf(format, prefix55, prefix56);

		String prefix57 = "(GMT0000) Monrovia";
		String prefix58 = "(GMT+0900) Seoul";
		System.out.printf(format, prefix57, prefix58);

		String prefix59 = "(GMT0000) Azores";
		String prefix60 = "(GMT+0930) Darwin";
		System.out.printf(format, prefix59, prefix60);

		String prefix61 = "(GMT+0100) Casablanca";
		String prefix62 = "(GMT+0930) Adelaide";
		System.out.printf(format, prefix61, prefix62);

		String prefix63 = "(GMT+0100) Greenwich Mean Time: Dublin, Edinburgh, Lisbon, London";
		String prefix64 = "(GMT+1000) Yakutsk";
		System.out.printf(format, prefix63, prefix64);

		String prefix65 = "(GMT+0100) West Central Africa";
		String prefix66 = "(GMT+1000) Brisbane";
		System.out.printf(format, prefix65, prefix66);

		String prefix67 = "(GMT+0200) Cairo";
		String prefix68 = "(GMT+1000) Guam, Port Moresby";
		System.out.printf(format, prefix67, prefix68);

		String prefix69 = "(GMT+0200) Harare, Pretoria";
		String prefix70 = "(GMT+1000) Hobart";
		System.out.printf(format, prefix69, prefix70);

		String prefix71 = "(GMT+0200) Sarajevo, Skopje, Warsaw, Zagreb";
		String prefix72 = "(GMT+1000) Canberra, Melbourne, Sydney";
		System.out.printf(format, prefix71, prefix72);

		String prefix73 = "(GMT+0200) Brussels, Copenhagen, Madrid, Paris";
		String prefix74 = "(GMT+1100) Vladivostok";
		System.out.printf(format, prefix73, prefix74);

		String prefix75 = "(GMT+0200) Belgrade, Bratislava, Budapest, Ljubljana, Prague";
		String prefix76 = "(GMT+1100) Magadan, Solomon Is., New Caledonia";
		System.out.printf(format, prefix75, prefix76);

		String prefix77 = "(GMT+0200) Amsterdam, Berlin, Bern, Rome, Stockholm, Vienna";
		String prefix78 = "(GMT+1200) Fiji, Marshall Is.";
		System.out.printf(format, prefix77, prefix78);

		String prefix79 = "(GMT+0300) Athens, Istanbul, Minsk";
		String prefix80 = "(GMT+1200) Guam, Kamchatka";
		System.out.printf(format, prefix79, prefix80);

		String prefix81 = "(GMT+0300) Bucharest";
		String prefix82 = "(GMT+1200) Auckland, Wellington";
		System.out.printf(format, prefix81, prefix82);

		String prefix83 = "(GMT+0300) Helsinki, Kyiv, Riga, Sofia, Tallinn, Vilnius";
		String prefix84 = "(GMT+1200) International Date Line West (Eniwetok, Kwajalein)";
		System.out.printf(format, prefix83, prefix84);

		String prefix85 = "(GMT+0300) Jerusalem";
		String prefix86 = "(GMT+1300) Nuku'alofa";
		System.out.printf(format, prefix85, prefix86);

		String prefix87 = "(GMT+0300) Kuwait, Riyadh";
		System.out.printf(format2, prefix87);
		
	}

	public static boolean isValidTZ(String tzidAnswer) {
		try{

			DecimalFormat df = new DecimalFormat("+#;-#");
			int tzInt = Integer.parseInt(df.parse(tzidAnswer).toString());

			if(((tzInt>=-1200&&tzInt<=1300)&&tzInt%100==0)||
					tzInt==-930||tzInt==-430||tzInt==-330||tzInt==330||
					tzInt==430||tzInt==530||tzInt==545||tzInt==630||
					tzInt==830||tzInt==845||tzInt==930||tzInt==1030||
					tzInt==1245){
		
				return true;
			}
		}catch (Exception e){
		return false;
		}
		return true;
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
	
	//when reading in files
		/**
		 * createArray
		 */
//		public static void createArray(){
//			String[][] tzArray = new String[87][2]; //87 = number of time zones, 2 = timezone GMT offset & timezone name
//			String fileName = "timezones.txt"; //file with timezones listed
//			String currentLine = "";
//			int count = 0;
//			
//			// Receives input from user about the file to be read
//			try {
//				// FileReader will read the text file
//				FileReader fileReader = new FileReader(fileName);
//			
//				// Wrap FileReader in BufferedReader
//				BufferedReader bufferedReader = new BufferedReader(fileReader);
//				
//				
//				// Read file line by line until the end
//				while((currentLine = bufferedReader.readLine()) != null){ //might change null to END:VCALENDAR
//					// System.out.println(currentLine); // Used for testing, just prints each line to screen
//					
//					// Format of timezones on timezone.txt file: (GMT-1000) Hawaii
//					// Timezone array [*][0] = GMT offset
//					// Timezone array [*][1] = timezone name
//					tzArray[count][0] = currentLine.substring(currentLine.indexOf('(')+4, currentLine.indexOf(')'));
//					tzArray[count][1] = currentLine.substring(currentLine.indexOf(')')+2);
//					
//					// Next line is for testing
//					// System.out.println(tzArray[count][0] + "\t" + tzArray[count][1]);
//					
//					count++;
//				}
//			// Close files
//			bufferedReader.close();
//			}
//					
//			// Outputs error message is fileName is not found
//			catch(FileNotFoundException ex1){
//				System.out.println("Unable to open file '" + fileName + "'.");
//			}
//			// Outputs error message if fileName cannot be read
//			catch(IOException ex2){
//				System.out.println("Error reading file '" + fileName + "'.");
//			}
//		}

}
