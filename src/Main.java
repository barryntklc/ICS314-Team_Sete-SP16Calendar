import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Calendar;
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
public class Main {

	private static String filename = "newcalendar.ics";
	private static ArrayList<ArrayList<String>> events = new ArrayList<ArrayList<String>>();

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
			//Scanner getInput = new Scanner(System.in);
			int choice = 0;
			// print choices here
			System.out.println("There are now ( " + events.size() + " ) events queued.");
			System.out.println("Events will be written to \"" + filename + "\".");
			System.out.println("(1) Change the name of your save file");
			System.out.println("(2) Add a new event");
			System.out.println("(3) List all events");
			System.out.println("(4) Exit");
			System.out.print(": ");

			//choice = getInput.nextInt();
			// throws an InputMismatchException when anything other than a
			// number is given

			System.out.println();
			if (choice == 1) { // change save file
				// change filename
				System.out.println("Changing the name of the save file...");
				//filename = getInput.nextLine();
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

	public static int getChoice() {
		Scanner getInput = new Scanner(System.in);
		
		return 0;
	}
	
	/**
	 * addEvent
	 * 
	 * adds a new event to the .ics file
	 */
	public static void addEvent() {
		ArrayList<String> details = new ArrayList<String>();
		Calendar cal = Calendar.getInstance();
		Date date = new Date();

		System.out.println(cal.toString());

		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMdd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");

		ArrayList<String> x = getEventDetails();
		
		// TODO Replace with editable values
		String description = "This is a test event";
		String location = "Hamilton Library";
		String summary = "Study for exam";
		String date_start = "20160201T230000Z";
		String date_end = "20160101T000001Z";
		String date_stamp = dateFormat.format(date) + "T" + timeFormat.format(date) + "Z";
		String date_created = dateFormat.format(date) + "T" + timeFormat.format(date) + "Z";
		String date_modified = dateFormat.format(date) + "T" + timeFormat.format(date) + "Z";

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
		details.add("SEQUENCE:1" + "\n");
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
		buffer.add("PRODID:-//Google Inc//Google Calendar 70.9054//EN" + "\n");
		buffer.add("VERSION:2.0" + "\n");
		buffer.add("CALSCALE:GREGORIAN" + "\n");
		buffer.add("METHOD:PUBLISH" + "\n");
		buffer.add("X-WR-CALNAME:" + calendar_name + "\n");
		buffer.add("X-WR-TIMEZONE:Pacific/Honolulu" + "\n");
		buffer.add("X-WR-CALDESC:" + "\n");

		for (int h = 0; h < events.size(); h++) {
			for (int i = 0; i < events.get(h).size(); i++) {
				buffer.add(events.get(h).get(i));
			}
		}
		buffer.add("END:VCALENDAR" + "\n");

		System.out.println(buffer);
		// System.out.println("DEBUG: toFile called!");

		/* System.out.println(input); */

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
	 * Asks the user for input on
	 * 
	 * @return an ArrayList of user input
	 */
	private static ArrayList<String> getEventDetails() {
		ArrayList results = new ArrayList<String>();

		// questions are asked to the user here

		return results;
	}

	// TODO add a menu where users can choose to add multiple events before
	// quitting.
}
