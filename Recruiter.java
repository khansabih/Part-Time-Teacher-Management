import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Recruiter extends PartTimeTeacherManagement {

    private AbsentReportFileHandler fileHandler;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    Recruiter(String user_id, String username, String email_id, String password, String role, AbsentReportFileHandler fileHandler) {
        super(user_id, username, email_id, password, role);
        this.fileHandler = fileHandler;
    }

    public void showChoices() {
        do {
            System.out.println("\tWelcome Recruiter!!\t");
            System.out.println("\t----------------------------------------------\t");
            // Give them a choice
            System.out.println("Chose options below to continue - ");
            System.out.println("1 - Mark absent and replacement");
            System.out.println("2 - Exit");
            System.out.print("Enter choice : ");
            
            try {
                int pptChoice = Integer.parseInt(reader.readLine());

                if (pptChoice == 1) {
                    this.getAbsentData();
                } else if (pptChoice == 2) {
                    this.exitRecruiter();
                    break;
                } else {
                    System.out.println("Enter valid input");
                }
            } catch (NumberFormatException | IOException e) {
                System.out.println("Error reading input. Please enter a valid choice.");
            }
        } while (true);
    }

    private void exitRecruiter() {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getAbsentData() {
        ArrayList<String> absentRecord = new ArrayList<>();
        //absentRecord.add(this.user_id);

        try {
            System.out.print("Enter UID of the absent teacher ");
            absentRecord.add(reader.readLine());

            System.out.print("Enter date of absence : ");
            absentRecord.add(reader.readLine());

            System.out.print("Enter UID of the replacement teacher : ");
            absentRecord.add(reader.readLine());

            absentRecord.add("false");
            this.fileHandler.writeToAbsentReport(absentRecord);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // public static void main(String[] args) {
    //     AbsentReportFileHandler fileHandler = new AbsentReportFileHandler();
    //     Recruiter ppt = new Recruiter("USER_LOGGED_IN", "USER_LOGGED_IN", "USER_LOGGED_IN", "USER_LOGGED_IN", "USER_LOGGED_IN", fileHandler);
    //     ppt.showChoices();
    // }
}
