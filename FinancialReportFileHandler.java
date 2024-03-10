import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FinancialReportFileHandler {
    private RandomAccessFile  financialReport;
    private String financialReportPath = "./financial_report.txt";  

    FinancialReportFileHandler() {
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

    void writeToFinancialReport(){
        try {
            this.financialReport.seek(this.financialReport.length());
            Scanner scanner = new Scanner(System.in);
            ArrayList<String> financialReportRecord = new ArrayList<>();
            
            System.out.println("Writing to file");

            System.out.print("Enter UID of the claimee : ");
			financialReportRecord.add(scanner.nextLine());
			System.out.print("Enter Name of the claimee : ");
			financialReportRecord.add(scanner.nextLine());
			System.out.print("Enter Role of the claimee : ");
			financialReportRecord.add(scanner.nextLine());
			System.out.print("Enter the date of the claim : ");
			financialReportRecord.add(scanner.nextLine());
			System.out.print("Enter the claim amount : ");
			financialReportRecord.add(scanner.nextLine());
			
			financialReportRecord.add("false");


            this.financialReport.writeBytes(String.join(",", financialReportRecord) + "\n");
            scanner.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static void main(String[] args) {
        FinancialReportFileHandler fileHandler = new FinancialReportFileHandler();

        // View initial report
        fileHandler.viewFinancialReport();
        // // Update report
        // fileHandler.writeToFinancialReport();
        // // View updated report
        // fileHandler.viewFinancialReport();
    }
}
