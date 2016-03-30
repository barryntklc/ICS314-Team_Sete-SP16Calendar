import java.util.ArrayList;
import java.util.Scanner;

/**
 * ReadCal
 * 
 * Methods used to read a .ics calendar can be written here.
 * 
 * @author Chunmeista
 */

public class CalendarFile {

	String fileContents = "";
	ArrayList<ArrayList<String>> calendar = new ArrayList<ArrayList<String>>();
	
	public CalendarFile(String filename) {
		//given a filename, read its contents into the ArrayList of ArrayLists
		//each ArrayList is a PARAMETER : VALUE pair, split by colon as the delimiter
		
		System.out.println("Given filename: " + filename);
		
		Scanner file = new Scanner(filename);
		
			//System.
		while (file.hasNext()) {
			System.out.print(file.nextLine());
		}
		
		file.close();
	}
	
}
