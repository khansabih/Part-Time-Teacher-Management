import java.io.*;
import java.util.*;
class PartTimeTeacherManagement{

	// Reader for user input from the terminal
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	// Reader and writer for user authentication
	// static BufferedReader authenticationFileReader = new BufferedReader(new FileReader("user_authentication.txt"));
	// static BufferedWriter authenticationFileWriter = new BufferedWriter(new FileWriter("user_authentication.txt"));
	// Reader and writer for main dataset
	// static BufferedReader mainDatasetReader = new BufferedReader(new FileReader("main_dataset.txt"));
	// static BufferedWriter mainDatasetWriter = new BufferedWriter(new FileWriter("main_dataset.txt"));

	String user_id, username, email_id, password, role;
	static boolean account_creation_status = false;

	PartTimeTeacherManagement(String user_id, String username, String email_id, String password, String role){
		this.user_id = user_id;
		this.username = username;
		this.email_id = email_id;
		this.password = password;
		this.role = role;
	}


	public static void createUserAccount() throws IOException{
		System.out.println("Let's get you going...");
		System.out.println("----------------------");
		System.out.print("Enter email id : ");
		String tempEmail = br.readLine().trim();
		System.out.print("Create password : ");
		String tempPass = br.readLine().trim();
		System.out.print("Create username : ");
		String tempName = br.readLine().trim();
		System.out.println("Chose your role : ");
		System.out.println("1 - Course director");
		System.out.println("2 - Class director");
		System.out.println("3 - Recruiter");
		System.out.println("4 - PTT director");
		System.out.print("Enter role(enter the number in front of the respective role and press enter) : ");
		int tempRole = Integer.parseInt(br.readLine().trim());
		while(tempRole < 0 || tempRole > 4){
			System.out.println("Choice doesn't exist... please enter a valid choice : ");
			tempRole = Integer.parseInt(br.readLine().trim());
		}

		// System.out.println("So the details are - ");
		// System.out.println("Email : "+tempEmail);
		// System.out.println("Password : "+tempPass);
		// System.out.println("Username : "+tempName);
		// System.out.println("role : "+tempRole);

		// If all details are right, then write the details in the user_auth text file
		String auth_results = tempEmail+","+tempPass+","+tempName+","+tempRole+"\n";
		BufferedWriter authenticationFileWriter = new BufferedWriter(new FileWriter("user_authentication.txt", true));
		try{
			authenticationFileWriter.write(auth_results);
		}catch(Exception e){
			System.out.println("Sorry, couldn't create account due to the following reason : ");
			System.out.println(e);
		}finally{
			account_creation_status = true;
		}

		authenticationFileWriter.close();

	}

	public static void loginUser(){

	}

	public static void main(String[] args) throws IOException{
		// Welcome message
		System.out.println("\tWelcome to Part time teacher management portal\t");
		System.out.println("\t----------------------------------------------\t");

		// Give them a choice
		System.out.println("Chose options below to continue - ");
		System.out.println("1 - Create account");
		System.out.println("2 - Already have an account & login");
		System.out.print("Enter choice : ");
		int auth_choice = Integer.parseInt(br.readLine());

		if(auth_choice == 1){
			createUserAccount();
		}

		
	}
}