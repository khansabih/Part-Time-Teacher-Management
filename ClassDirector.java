import java.io.*;
import java.util.*;
class ClassDirector{

	// Global reader and writer
	static fileReadAndWrite classDirectorReaderAndWriter = new fileReadAndWrite();

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

	ClassDirector(){}

	// Utility class for requirement, basically user defined variable/object - Start
	static class Requirement implements Comparable<Requirement>{
		String requirementUserID;
		String date_of_joining;
		int number_of_teacher;
		boolean requirementStatus;
		int requirementAlloted;
		String newRequirementID;

		Requirement(String date_of_joining, int number_of_teacher, boolean requirementStatus, int requirementAlloted,
			String current_user_id, String newRequirementID
		){
			this.date_of_joining = date_of_joining;
			this.number_of_teacher = number_of_teacher;
			requirementUserID = current_user_id;
			this.requirementStatus = requirementStatus;
			this.requirementAlloted = requirementAlloted;
			this.newRequirementID = newRequirementID;
			// Generating a unique id for the new user
			// newRequirementID = UUID.randomUUID().toString().substring(0,6);
		}

		@Override
		public int compareTo(Requirement req){
			return this.number_of_teacher - req.number_of_teacher;
		}
	}
	// Utility class for requirement, basically user defined variable/object - End

	// Utility method to add requirement to the database
	static void addRequirementToDatabase(Requirement requirement) throws IOException{
		classDirectorReaderAndWriter.writeToFile("class_director.txt", requirement);
	}

	// Method to get the requirements
	static void inputRequirements() throws IOException{
		// Flag to set whenever we are done with inputting.
		String endInput = "Y";
		// An arraylist to add the requirements as necessary.
		ArrayList<Requirement> class_director_requirements = new ArrayList<Requirement>();
		// A while loop running until the time the flag is set to terminate it.
		while(endInput.equalsIgnoreCase("Y")){
			System.out.println("\n------------------------");
			System.out.println("Requirement input : ");
			System.out.println("--------------------------");
			System.out.print("Date of Joining(DD/MM/YYYY) : ");
			String dateOfJoining = classDirectorReader.readLine().trim();
			System.out.print("Number of teachers required : ");
			int numberOfTeachers = Integer.parseInt(classDirectorReader.readLine().trim());

			// Creating a requirements object with a unique id
			String reqID = UUID.randomUUID().toString().substring(0,6);
			Requirement newRequirement = new Requirement(dateOfJoining, numberOfTeachers, false, 0, user_id, reqID);

			// Show the user a confirmation of their entry.
			System.out.println("\n-----------------------------------------");
			System.out.println("Are you sure you want to make this entry : ");
			System.out.println("-------------------------------------------");
			System.out.println("Date Of Joining: "+newRequirement.date_of_joining);
			System.out.println("Number of teachers required: "+newRequirement.number_of_teacher);
			System.out.print("Type 'Y' for Yes and 'N' for No and press Enter: ");
			String tempEntryInput = classDirectorReader.readLine().trim();

			// Adding this requirement to our database
			if(tempEntryInput.equalsIgnoreCase("Y")){
				addRequirementToDatabase(newRequirement);
			}

			// Getting the input for the flag, whether to stop getting the input or not.
			System.out.print("\nWant to add another requirement(Type 'Y' for 'Yes' and 'N' for 'No' and press enter) : ");
			endInput = classDirectorReader.readLine().trim();
		}
	}

	// For writing into the database
	static void makeAllotment(String requirementID, int allotmentCount) throws IOException{
		StringBuilder temporaryDatabaseBuilder = new StringBuilder();
		BufferedReader allotmentReader = new BufferedReader(new FileReader("class_director.txt"));
		String requirementEntry;
		while((requirementEntry = allotmentReader.readLine()) != null){
			String requirementEntryVals[] = requirementEntry.split(",");
			String reqId = requirementEntryVals[0];
			if(reqId.equals(requirementID)){
				// Get the original values of the entry.
				String reqID = reqId;
				String reqUserID = requirementEntryVals[1];
				String doj = requirementEntryVals[2];
				int number_of_teachers = Integer.parseInt(requirementEntryVals[3]);
				int alloted = allotmentCount;
				boolean status = true;

				// Create the new and modified string.
				String modifiedEntry = reqId+","+reqUserID+","+doj+","+number_of_teachers+","+alloted+","+status+"\n";

				// Add this modified entry into the new temporary dataset.
				temporaryDatabaseBuilder.append(modifiedEntry);
			}else{
				String reqEntry = requirementEntry+"\n";
				temporaryDatabaseBuilder.append(reqEntry);
			}
		}

		// Temporary writer here.
		BufferedWriter allotmentWriter = new BufferedWriter(new FileWriter("class_director.txt"));
		allotmentWriter.write(temporaryDatabaseBuilder.toString());

		System.out.println("Succesffully made the allotments");

		allotmentReader.close();
		allotmentWriter.close();
	}

	// Utility function to get the requirement where status is pending
	static ArrayList<Requirement> getPendingRequirements() throws IOException{
		// Arraylist to accomodate all the values
		ArrayList<Requirement> pendingRequirements = new ArrayList<Requirement>();
		// Reading from file
		BufferedReader pendingRequirementReader = classDirectorReaderAndWriter.readFromFile("class_director.txt");
		String requirementText;
		int reqLine = 0;
		while((requirementText = pendingRequirementReader.readLine()) != null){
			if(reqLine > 0){
				String reqEntry[] = requirementText.trim().split(",");
				if(reqEntry[5].equals("false")){
					String reqID = reqEntry[0];
					String userID = reqEntry[1];
					String doj = reqEntry[2];
					int techerCount = Integer.parseInt(reqEntry[3]);
					int allotedCount = Integer.parseInt(reqEntry[4]);
					boolean status = Boolean.parseBoolean(reqEntry[5]);

					// Creating a new requirement object
					Requirement newReq = new Requirement(
						doj, techerCount, status, allotedCount, userID, reqID
					);

					// Adding the requirement to the list
					pendingRequirements.add(newReq);
				}
			}

			reqLine++;
		}

		pendingRequirementReader.close();
		return pendingRequirements;
	}
	// For seeing all the requirements.
	static void getAllRequirementsByClassDirector() throws IOException{
		System.out.println("--------------------------------------------");
		System.out.println("Hi, here you can access all the requirements");
		System.out.println("--------------------------------------------");
		System.out.println("Chose filter from below to get requirement data : ");
		System.out.println("1 - Get by requirementID");
		System.out.println("2 - Get all pending requirements");
		System.out.print("Enter choice : ");
		int requirementFilterChoice = Integer.parseInt(classDirectorReader.readLine().trim());
		if(requirementFilterChoice == 1){

		}else if(requirementFilterChoice == 2){
			ArrayList<Requirement> pendingRequirementsList = getPendingRequirements();
			System.out.println("REQID\t\tDOJ\t\t   TEACHERS\t\tSTATUS\n");
			for(Requirement requirement: pendingRequirementsList){
				System.out.println(requirement.newRequirementID+"\t|\t"+requirement.date_of_joining+
					"\t|\t"+requirement.number_of_teacher+"\t|\t"+requirement.requirementStatus
				);
			}
		}
	}

	static void initialChoice() throws IOException{
		System.out.println("-----------------------------");
		System.out.println("Welcome "+username);
		System.out.println("-----------------------------");
		System.out.println("What would you like to do today ? Chose an option below(Put in the option number and press enter) : ");
		System.out.println("1 - Enter requirements");
		System.out.println("2 - Search requirements");
		int class_director_choice = Integer.parseInt(classDirectorReader.readLine().trim());

		switch(class_director_choice){
			case 1: inputRequirements();
					break;
			case 2: getAllRequirementsByClassDirector();
				    break;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	}
}
