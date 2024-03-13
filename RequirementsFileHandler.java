import java.io.*;
import java.util.ArrayList;

public class RequirementsFileHandler {
    private RandomAccessFile  absentReport;
    private String absentReportFilePath = "./requirement_file.txt";  

    RequirementsFileHandler() {
        try {
            this.absentReport = new RandomAccessFile (this.absentReportFilePath, "rw");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void viewPendingRequirements() {
        try {
            String line;
            this.absentReport.seek(0);
            while ((line = this.absentReport.readLine()) != null) {
                String[] fields = line.split(",");
                
                if (fields.length >= 4 && fields[3].trim().equalsIgnoreCase("false")) {
                    for (String field : fields) {
                        System.out.print(field + " ");
                    }
                    // Move to the next line
                    System.out.println();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void writeCourseRequirementFile(ArrayList <String> absentReportRecord){
        try {
            this.absentReport.seek(this.absentReport.length());
            this.absentReport.writeBytes(String.join(",", absentReportRecord) + "\n");
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    void approveRequestByRequestID(String requestID) {
        try {
            long currentPosition = this.absentReport.getFilePointer();
            this.absentReport.seek(0);
            String line;
            while ((line = this.absentReport.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length > 0 && fields[0].trim().equals(requestID)) {
                    if (fields.length >= 4) {
                        fields[3] = "true"; // Update approval status to true
                        // Update the record in the file
                        this.absentReport.seek(currentPosition);
                        this.absentReport.writeBytes(String.join(",", fields));
                        break; // Stop searching after finding the record
                    }
                }
                currentPosition = this.absentReport.getFilePointer();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
