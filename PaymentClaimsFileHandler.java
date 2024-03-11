import java.io.*;
import java.util.ArrayList;

public class PaymentClaimsFileHandler {
    private RandomAccessFile  financialReport;
    private String financialReportPath = "./payment_claims.txt";  

    PaymentClaimsFileHandler() {
        try {
            this.financialReport = new RandomAccessFile (this.financialReportPath, "rw");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void viewFinancialReport(){
        try{
            String line;
            this.financialReport.seek(0);
            while ((line = this.financialReport.readLine()) != null) {
                // Split the line into fields using a comma as the separator
                String[] fields = line.split(",");
                
                // Process each field as needed
                for (String field : fields) {
                    System.out.print(field + " ");
                }
                
                // Move to the next line
                System.out.println();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    void writeToPaymentClaims(ArrayList<String> claimRecord){
        try {
            this.financialReport.seek(this.financialReport.length());
            this.financialReport.writeBytes(String.join(",", claimRecord) + "\n");
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
