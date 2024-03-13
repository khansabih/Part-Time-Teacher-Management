import java.io.*;
import java.util.*;

public class CourseDirector extends PartTimeTeacherManagement {
	
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private fileReadAndWrite classDirectorReaderAndWriter = new fileReadAndWrite();
	private RequirementsFileHandler fileHandler;
   // private RequestsFileHandler fileHandler;


	CourseDirector(String user_id, String username, String email_id, String password, String role, RequirementsFileHandler fileHandler ) {
		super(user_id, username, email_id, password,role);
		this.fileHandler = fileHandler;
		// TODO Auto-generated constructor stub
	}

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
	
	public void showChoices() {
		boolean runChoiceLoop = true;
		do {
			System.out.println("\tWelcome Course Director - "+ this.username + "!!\t");
			System.out.println("\t----------------------------------------------\t");
			// Give them a choice
			System.out.println("Chose options below to continue - ");
			System.out.println("1 - Make a new Request");
			System.out.println("2 - Check pending requests");
			System.out.println("3 - Make allotment");
			System.out.println("4- Exit");
			System.out.print("Enter choice : ");
			try {
				int Choice = Integer.parseInt(reader.readLine());
		
				switch(Choice) {
				
				case 1 : 
						System.out.println("Please Enter the details below to raise a new request:");
						this.floatRequest();
						System.out.println("Request raised to PTT Director for approval.");
						break;
				case 2 :
						System.out.println("View request approval status");
						this.getAllRequirementsByClassDirector();
						break;
				case 3 :
						System.out.println("Check the requirements floated by Class Director. Insert case ID to make changes in requirement");
						System.out.print("Enter the RequestID:");
						String reqID = this.reader.readLine().trim();
						System.out.print("Enter the Number:");
						int allotedTeachers = Integer.parseInt(this.reader.readLine().trim());
						this.makeAllotment(reqID, allotedTeachers);
						break;
				case 4 :
						System.out.println("You have exited");
						this.exitCourseDirector();
						runChoiceLoop = false;
						break;
				default :
						System.out.println("Please enter a valid input");
						break;
				}
			} catch (NumberFormatException | IOException e) {
				System.out.println("Error reading input. Please enter a valid choice.");
			}
		} while (runChoiceLoop);
	}

	private void exitCourseDirector() {
		try {
			this.reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	        

	private void floatRequest() {
		ArrayList<String> requestRecord = new ArrayList<>();
		String reqID = UUID.randomUUID().toString().substring(0,6);
		requestRecord.add(reqID);

		try {
			System.out.print("Enter the date of the Request : ");
			requestRecord.add(reader.readLine());

			System.out.print("Enter the request: ");
			requestRecord.add(reader.readLine());

			requestRecord.add("false");                
			this.fileHandler.writeCourseRequirementFile(requestRecord);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// For writing into the database
	void makeAllotment(String requirementID, int allotmentCount) throws IOException{
		StringBuilder temporaryDatabaseBuilder = new StringBuilder();
		BufferedReader allotmentReader = new BufferedReader(new FileReader("class_director.txt"));
		String requirementEntry;
		while((requirementEntry = allotmentReader.readLine()) != null){
			String requirementEntryVals[] = requirementEntry.split(",");
			String reqId = requirementEntryVals[0];
			if(reqId.equals(requirementID)){
				// Get the original values of the entry.
				String reqUserID = requirementEntryVals[1];
				String doj = requirementEntryVals[2];
				int number_of_teachers = Integer.parseInt(requirementEntryVals[3]);
				int alloted = allotmentCount;
				boolean status = false;

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
	ArrayList<Requirement> getPendingRequirements() throws IOException{
		// Arraylist to accomodate all the values
		ArrayList<Requirement> pendingRequirements = new ArrayList<Requirement>();
		// Reading from file
		BufferedReader pendingRequirementReader = classDirectorReaderAndWriter.readFromFile("class_director.txt");
		String requirementText;
		int reqLine = 0;
		while((requirementText = pendingRequirementReader.readLine()) != null){
			if(reqLine > 0){
				String reqEntry[] = requirementText.trim().split(",");
				if(reqEntry[4].equals("0")){
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
	void getAllRequirementsByClassDirector() throws IOException{
		System.out.println("--------------------------------------------");
		System.out.println("Hi, here you can access all the requirements");
		ArrayList<Requirement> pendingRequirementsList = getPendingRequirements();
		System.out.println("REQID\t\tDOJ\t\t   TEACHERS\t\tSTATUS\n");
		for(Requirement requirement: pendingRequirementsList){
			System.out.println(requirement.newRequirementID+"\t|\t"+requirement.date_of_joining+
				"\t|\t"+requirement.number_of_teacher+"\t|\t"+requirement.requirementStatus
			);
		}
		
	}

	public static void main(String[] args) {
		
	}


}

