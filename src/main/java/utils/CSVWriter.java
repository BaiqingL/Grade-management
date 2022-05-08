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
            String assignmentFormat = a.getName() + " (" + a.getMaxGrade() + ", " + a.getWeight() + "%)";
            header.add(assignmentFormat);
        }

        // Start the student records
        List<List<String>> studentRecords = new ArrayList<>();
        for (Student s : gc.getStudents()) {
            List<String> studentRecord = new ArrayList<>();
            studentRecord.add(s.getName());
            studentRecord.add(String.valueOf(s.getBUID()));
            for (Assignment a : s.getAssignments()) {
                studentRecord.add(String.valueOf(a.getGrade()));
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
}
