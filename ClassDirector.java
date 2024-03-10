import java.io.*;
import java.util.*;
class ClassDirector{

	static BufferedReader classDirectorReader = new BufferedReader(new InputStreamReader(System.in));

	static String user_id, email, username, password;
	static int role;

	ClassDirector(String user_id, String email, String username, String password, int role){
		this.user_id = user_id;
		this.email = email;
		this.username = username;
		this.password = password;
		this.role = role;
	}

	// Utility class for requirement, basically user defined variable/object - Start
	static class Requirement implements Comparable<Requirement>{
		String date_of_joining;
		int number_of_teacher;

		Requirement(String date_of_joining, int number_of_teacher){
			this.date_of_joining = date_of_joining;
			this.number_of_teacher = number_of_teacher;
		}

		@Override
		public int compareTo(Requirement req){
			return this.number_of_teacher - req.number_of_teacher;
		}
	}
	// Utility class for requirement, basically user defined variable/object - End

	// Method to get the requirements
	static void inputRequirements() throws IOException{
		// Flag to set whenever we are done with inputting.
		String endInput = "Y";
		// An arraylist to add the requirements as necessary.
		ArrayList<Requirement> class_director_requirements = new ArrayList<Requirement>();
		// A while loop running until the time the flag is set to terminate it.
		while(endInput.equalsIgnoreCase("Y")){
			System.out.print("Date of Joining(DD/MM/YYYY) : ");
			String dateOfJoining = classDirectorReader.readLine().trim();
			System.out.print("Number of teachers required : ");
			int numberOfTeachers = Integer.parseInt(classDirectorReader.readLine().trim());

			// Creating a requirements object
			Requirement newRequirement = new Requirement(dateOfJoining, numberOfTeachers);

			// Show the user a confirmation of their entry.
			System.out.println("Are you sure you want to make this entry : ");
			System.out.println("Date Of Joining: "+newRequirement.date_of_joining);
			System.out.println("Number of teachers required: "+newRequirement.number_of_teacher);
			
			// Adding this requirement to our list
			class_director_requirements.add(newRequirement);

			// Getting the input for the flag, whether to stop getting the input or not.
			System.out.print("Want to add another requirement(Type 'Y' for 'Yes' and 'N' for 'No' and press enter) : ");
			endInput = classDirectorReader.readLine().trim();
		}
	}

	static void initialChoice() throws IOException{
		System.out.println("Welcome "+username);
		System.out.println("-----------------------------");
		System.out.println("What would you like to do today ? Chose an option below(Put in the option number and press enter) : ");
		System.out.println("1 - Enter requirements");
		System.out.println("2 - Search requirements");
		int class_director_choice = Integer.parseInt(classDirectorReader.readLine().trim());

		switch(class_director_choice){
			case 1: inputRequirements();
			case 2: System.exit(0);
		}
	}
}
