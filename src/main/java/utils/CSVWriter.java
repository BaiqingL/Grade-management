package utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVWriter {

    public static void writeGradedClass(GradedClass gc, String fileOutName) throws IOException {
        // Write the header
        List<String> header = new ArrayList<>();
        header.add("Student Name");
        header.add("BUID");
        List<Assignment> assignments = gc.getAssignments();
        for (Assignment a : assignments) {
            String assignmentFormat = String.format("%s (%d, %d%%) (%s,%s)",
                    a.getName(),
                    a.getMaxGrade(),
                    a.getWeight(),
                    a.getAssignedDate(),
                    a.getDueDate());
            header.add(assignmentFormat);
            header.add("Submission Date");
        }

        // Start the student records
        List<List<String>> studentRecords = new ArrayList<>();
        for (Student s : gc.getStudents()) {
            List<String> studentRecord = new ArrayList<>();
            studentRecord.add(s.getName());
            studentRecord.add(String.valueOf(s.getBUID()));
            for (Assignment a : s.getAssignments()) {
                studentRecord.add(String.valueOf(a.getGrade()));
                studentRecord.add(String.valueOf(a.getSubmissionDate()));
            }
            studentRecords.add(studentRecord);
        }

        // Write the CSV file
        CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(fileOutName), CSVFormat.DEFAULT);
        csvPrinter.printRecord(header);
        for (List<String> studentRecord : studentRecords) {
            csvPrinter.printRecord(studentRecord);
        }
        csvPrinter.flush();
        csvPrinter.close();
    }

    public static void writeFinalGrades(String outputFilePath, String outputFileName, String[] headers, String[][] data) throws IOException {
        String fileOutName = outputFilePath + "/" + outputFileName + ".csv";
        CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(fileOutName), CSVFormat.DEFAULT);
        csvPrinter.printRecord(headers);
        for (String[] row : data) {
            csvPrinter.printRecord(row);
        }
        csvPrinter.flush();
        csvPrinter.close();
    }
}
