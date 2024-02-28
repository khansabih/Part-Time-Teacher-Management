import java.io.*;
import java.util.*;
class PartTimeTeacherManagement{

	// Reader for user input from the terminal
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	String user_id, username, email_id, password, role;
	static boolean ACCOUNT_CREATION_STATUS = false;
	static String USER_LOGGED_IN = "LOGGED_OUT";

	PartTimeTeacherManagement(String user_id, String username, String email_id, String password, String role){
		this.user_id = user_id;
		this.username = username;
		this.email_id = email_id;
		this.password = password;
		this.role = role;
	}


	public static void createUserAccount(String user_values[]) throws IOException{
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

		// Generating a unique id for the new user
		String newUserID = UUID.randomUUID().toString();

		// If all details are right, then write the details in the user_auth text file
		String auth_results = newUserID+","+tempEmail+","+tempPass+","+tempName+","+tempRole+"\n";
		BufferedWriter authenticationFileWriter = new BufferedWriter(new FileWriter("user_authentication.txt", true));
		try{
			authenticationFileWriter.write(auth_results);

			// Since the user is created, so we can straightaway log them in.
			user_values[0] = newUserID;
			user_values[1] = tempEmail;
			user_values[2] = tempPass;
			user_values[3] = tempName;
			user_values[4] = String.valueOf(tempRole);
			USER_LOGGED_IN = "SUCCESS";

		}catch(Exception e){
			System.out.println("Sorry, couldn't create account due to the following reason : ");
			System.out.println(e);
		}

		authenticationFileWriter.close();

	}

	public static void loginUser(String user_values[]) throws IOException{

		int login_attempts = 0;
		
		while(!USER_LOGGED_IN.equals("SUCCESS")){
			BufferedReader authFileReader = new BufferedReader(new FileReader("user_authentication.txt"));
			login_attempts++;
			if(login_attempts > 1){
				System.out.println("Sorry your credentials don't match, please try again");
			}
			System.out.println("Enter your credentials below : ");
			System.out.println("-------------------------------");
			System.out.print("Email : ");
			String loginEmail = br.readLine().trim();
			System.out.print("Password : ");
			String loginPassword = br.readLine().trim();

			// Read the auth dataset now.
			String authFileText;
			try{
				int line = 0;
				while((authFileText = authFileReader.readLine()) != null){
					if(line != 0){
						String user_details[] = authFileText.trim().split(",");

						// First match the email id 
						if(loginEmail.equals(user_details[1])){
							// If this matches, the process should stop here, get all the values from the row
							// and match with the password.
							if(loginPassword.equals(user_details[2])){
								// If both the credentials match, get the other values and then break out of the loop.
								user_values[0] = user_details[0];
								user_values[1] = user_details[1];
								user_values[2] = user_details[2];
								user_values[3] = user_details[3];
								user_values[4] = user_details[4];
								USER_LOGGED_IN = "SUCCESS";
								break;
							}
						}

						line++;
					}
					line++;
				}
			}catch(Exception loginException){
				USER_LOGGED_IN = "FAILED";
			}

			authFileReader.close();
		}
	}

	public static void main(String[] args) throws IOException{
		// Welcome messages
		System.out.println("\tWelcome to Part time teacher management portal\t");
		System.out.println("\t----------------------------------------------\t");

		// Give them a choice
		System.out.println("Chose options below to continue - ");
		System.out.println("1 - Create account");
		System.out.println("2 - Already have an account & login");
		System.out.print("Enter choice : ");
		int auth_choice = Integer.parseInt(br.readLine());

		// An array to hold logged in user's values
		String user_values[] = new String[5];

		if(auth_choice == 1){
			createUserAccount(user_values);
		}
		if(auth_choice == 2){
			loginUser(user_values);
		}

		if(USER_LOGGED_IN.equals("SUCCESS")){
			System.out.println("USER LOGGED IN SUCCESSFULLY");
			System.out.println("---------------------------");
			System.out.println("UserID : "+user_values[0]);
			System.out.println("Email : "+user_values[1]);
			System.out.println("Password : "+user_values[2]);
			System.out.println("Username : "+user_values[3]);
			System.out.println("Role : "+user_values[4]);
		}
		
	}
}