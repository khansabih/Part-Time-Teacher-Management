import java.io.*;
import java.util.ArrayList;
//import java.util.Scanner;

public class AbsentReportFileHandler {

    private RandomAccessFile  absentReport;
    private String absentReportFilePath = "./absent_report.txt";  

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

    void writeToAbsentReport(ArrayList <String> absentReportRecord){
        try {
            this.absentReport.seek(this.absentReport.length());
            this.absentReport.writeBytes(String.join(",", absentReportRecord) + "\n");
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    

    // public static void main(String[] args) {
    //     AbsentReportFileHandler fileHandler = new AbsentReportFileHandler();

    //     // View initial report
    //     fileHandler.viewAbsentReport();
    // }
}