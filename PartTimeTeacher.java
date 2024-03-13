import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PartTimeTeacher extends PartTimeTeacherManagement {

    private PaymentClaimsFileHandler fileHandler;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    PartTimeTeacher(String user_id, String username, String email_id, String password, String role, PaymentClaimsFileHandler fileHandler) {
        super(user_id, username, email_id, password, role);
        this.fileHandler = fileHandler;
    }

    public void showChoices() {
        do {
            System.out.println("\tWelcome PPT - "+ this.username + "!!\t");
            System.out.println("\t----------------------------------------------\t");
            // Give them a choice
            System.out.println("Chose options below to continue - ");
            System.out.println("1 - Add claim");
            System.out.println("2 - Exit");
            System.out.print("Enter choice : ");
            
            try {
                int pptChoice = Integer.parseInt(reader.readLine());

                if (pptChoice == 1) {
                    this.getPaymentClaim();
                } else if (pptChoice == 2) {
                    this.exitPartTimeTeacher();
                    break;
                } else {
                    System.out.println("Enter valid input");
                }
            } catch (NumberFormatException | IOException e) {
                System.out.println("Error reading input. Please enter a valid choice.");
            }
        } while (true);
    }

    private void exitPartTimeTeacher() {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getPaymentClaim() {
        ArrayList<String> claimRecord = new ArrayList<>();
        claimRecord.add(this.user_id);

        try {
            System.out.print("Enter the date of the claim : ");
            claimRecord.add(reader.readLine());

            System.out.print("Enter the claim amount : ");
            claimRecord.add(reader.readLine());

            claimRecord.add("false");
            this.fileHandler.writeToPaymentClaims(claimRecord);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
