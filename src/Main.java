import java.io.FileNotFoundException;
import java.io.PrintWriter;
<<<<<<< HEAD
import java.util.Scanner;
=======
import java.util.ArrayList;
>>>>>>> origin/master

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
	 * 
	 */
	public static void mainMenu() {
		boolean exit = false;
		Scanner getInput = new Scanner(System.in);
		
		while (!exit) {
			int choice = 0;
			//print choices here
			System.out.println("(1) Change the name of your save file");
			System.out.println("(2) Add a new save file");
			System.out.println("(3) Exit");
			System.out.print(": ");
			
			choice = getInput.nextInt();
			//throws an InputMismatchException when anthing other than a number is given
			
			if (choice == 1) {
				//change filename
				System.out.println("Changing the name of the save file...");
			} else if (choice == 2) {
				//add an event
				System.out.println("Creating an event...");
			} else if (choice == 3) {
				//exit
				System.out.println("Exiting Calendar_IO");
				exit = true;
			} else {
				System.out.println("ERROR: Invalid choice!");
			}
		}
		
		
		String calendar_name = "TESTIMPORT";
		
		//TODO Replace with editable values
		String description = "This is a test event";
		String location = "Hamilton Library";
		String summary = "Study for exam";
<<<<<<< HEAD
		
=======
>>>>>>> origin/master
		String date_start = "20160201T230000Z";
		String date_end = "20160101T000001Z";
		String date_stamp = "20160223T032827Z";
		String date_created = "20160223T032617Z";
		String date_modified = "20160223T032739Z";
		
		String output = "";

		output = output + "BEGIN:VCALENDAR" + "\n";
		output = output + "PRODID:-//Google Inc//Google Calendar 70.9054//EN" + "\n";
		output = output + "VERSION:2.0" + "\n";
		output = output + "CALSCALE:GREGORIAN" + "\n";
		output = output + "METHOD:PUBLISH" + "\n";
		output = output + "X-WR-CALNAME:" + calendar_name + "\n";
		output = output + "X-WR-TIMEZONE:Pacific/Honolulu" + "\n"; //timezone
		output = output + "X-WR-CALDESC:" + "\n";
		
		//TODO make DTSTART, DTEND, DTSTAMP, CREATED, and LAST-MODIFIED customizable by the user
		output = output + "BEGIN:VEVENT" + "\n";
		output = output + "DTSTART:" + date_start + "\n";
		output = output + "DTEND:" + date_end + "\n";
		output = output + "DTSTAMP:" + date_stamp + "\n";
		output = output + "UID:rhh4l2hdp1snqc7ego4lueh18c@google.com" + "\n";
		output = output + "CREATED:" + date_created + "\n";
		output = output + "DESCRIPTION:" + description + "\n";
		output = output + "LAST-MODIFIED:" + date_modified + "\n";
		output = output + "LOCATION:" + location + "\n";
		output = output + "SEQUENCE:1" + "\n";
		output = output + "STATUS:CONFIRMED" + "\n";
		output = output + "SUMMARY:" + summary + "\n";
		output = output + "TRANSP:TRANSPARENT" + "\n";
		output = output + "END:VEVENT" + "\n";
		
		output = output + "END:VCALENDAR" + "\n";

		toFile(output);
	}
	
	/**
	 * Writes given text that prints calendar data to a file here.
	 */
	private static void toFile(String input) {
		System.out.println("DEBUG: toFile called!");
		System.out.println(input);
		
		try {
			PrintWriter output = new PrintWriter(filename);
			output.write(input);
			output.close();
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: The file was not found.");
		}
	}
	
	/**
	 * Asks the user for input on 
	 * @return an ArrayList of user input
	 */
	private static ArrayList<String> getDetails() {
		ArrayList results = new ArrayList<String>();
		
		//questions are asked to the user here
		
		return results;
	}
	
	//TODO add a menu where users can choose to add multiple events before quitting.
}
