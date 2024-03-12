import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CourseDirector extends PartTimeTeacherManagement {
	
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
   // private RequestsFileHandler fileHandler;


	CourseDirector(String user_id, String username, String email_id, String password, String role ) {
		super(user_id, username, email_id, password,role);
		// TODO Auto-generated constructor stub
	}
	
	 public void showChoices() {
	        do {
	            System.out.println("\tWelcome Course Director - "+ this.username + "!!\t");
	            System.out.println("\t----------------------------------------------\t");
	            // Give them a choice
	            System.out.println("Chose options below to continue - ");
	            System.out.println("1 - Make a new Request");
	            System.out.println("2 - Check request approval status");
	            System.out.println("3 - Check and modify PTT recuirement");
	            System.out.println("4- Exit");
	            System.out.print("Enter choice : ");
	            
	            try {
	                int Choice = Integer.parseInt(reader.readLine());
         	
	            	switch(Choice) {
	            	
	            	case 1 : System.out.println("Please Enter the details below to raise a new request:");
	            			 this.floatRequest();
	            			 System.out.println("Request raised to PTT Director for approval.");
	            	break;
	            	case 2 : System.out.println("View request approval status");
	            	break;
	            	case 3 : System.out.println("Check the requirements floated by Class Director.Insert case ID to make changes in requirement");
	            	break;
	            	case 4 : System.out.println("You have exited");
	            			 exitCourseDirector();
	            	break;
	            	default : System.out.println("Please enter a valid input");
	            	break;

	            	}
	            } catch (NumberFormatException | IOException e) {
	                System.out.println("Error reading input. Please enter a valid choice.");
	            }
	        } while (true);
	 }
	        private void exitCourseDirector() {
	            try {
	                reader.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        

	        private void floatRequest() {
	            ArrayList<String> requestRecord = new ArrayList<>();
	            requestRecord.add(this.user_id);

	            try {
	                System.out.print("Enter the date of the Request : ");
	                requestRecord.add(reader.readLine());

	                System.out.print("Enter the request: ");
	                requestRecord.add(reader.readLine());

	                requestRecord.add("false");
//	                this.fileHandler.writeToPaymentClaims(requestRecord);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }

	    }

