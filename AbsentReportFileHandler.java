import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class AbsentReportFileHandler {

    private RandomAccessFile  absentReport;
    private String absentReportFilePath = "./absent_report.text";  

    AbsentReportFileHandler() {
        try {
            this.absentReport = new RandomAccessFile (this.absentReportFilePath, "rw");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void viewAbsentReport(){
        try{
            String line;
            this.absentReport.seek(0);
            while ((line = this.absentReport.readLine()) != null) {
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

    void writeToAbsentReport(){
        try {
            this.absentReport.seek(this.absentReport.length());
            Scanner scanner = new Scanner(System.in);
            ArrayList<String> absentReportRecord = new ArrayList<>();
            
            System.out.println("Writing to file");

            System.out.print("Enter UID of the absent teacher : ");
			absentReportRecord.add(scanner.nextLine());
			System.out.print("Enter Name of the absent teacher : ");
			absentReportRecord.add(scanner.nextLine());
			System.out.print("Enter the date of absence : ");
			absentReportRecord.add(scanner.nextLine());


            this.absentReport.writeBytes(String.join(",", absentReportRecord) + "\n");
            scanner.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static void main(String[] args) {
        AbsentReportFileHandler fileHandler = new AbsentReportFileHandler();

        // View initial report
        fileHandler.viewAbsentReport();
        // Update report
        fileHandler.writeToAbsentReport();
        // View updated report
        fileHandler.viewAbsentReport();
    }
}
