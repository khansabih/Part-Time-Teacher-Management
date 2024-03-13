import java.io.*;
class fileReadAndWrite{
	
	// Empty constructor
	fileReadAndWrite(){}

	// method to extract and build writing entry into the database
	public static <T> String buildClassDirectorEntry(T classDirectorObject){
		ClassDirector.Requirement reqObj = (ClassDirector.Requirement) classDirectorObject;
		// Extracting values from the object
		String requirementID = reqObj.newRequirementID;
		String userID = reqObj.requirementUserID;
		String date_of_joining = reqObj.date_of_joining;
		int number_of_teacher = reqObj.number_of_teacher;
		boolean requirementStatus = reqObj.requirementStatus;
		int requirementAlloted = reqObj.requirementAlloted;

		// Prompt text.
		System.out.println("\nYour Requirement has been recorded with");
		System.out.println("RequirementID = "+requirementID+"\n Please keep this ID handy for easy searching in the database.\n");

		// Making a comma separated entry using the extracted value to write to the file.
		return (requirementID+","+userID+","+date_of_joining+","+number_of_teacher+","+requirementAlloted+","+requirementStatus+"\n");
		
	}

	// method to read from the database
	public BufferedReader readFromFile(String databaseName) throws IOException{
		BufferedReader fileReader = new BufferedReader(new FileReader(databaseName));
		return fileReader;
	}

	// method to write to the database
	public static <T> void writeToFile(String databaseName, T writingObject) throws IOException{
		switch(databaseName){
			case "class_director.txt": 
				   BufferedWriter classDirectorWriter = new BufferedWriter(new FileWriter("class_director.txt", true));
				   String databaseEntry = buildClassDirectorEntry(writingObject);
				   classDirectorWriter.write(databaseEntry);
				   classDirectorWriter.close();
				   break;

		}
	}

}