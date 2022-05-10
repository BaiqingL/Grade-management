package utils;

import com.sun.nio.sctp.SendFailedNotification;
import gui.State;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVWriter {

    public static void writeGradedClass(GradedClass gc, String fileOutName) throws IOException {
        // Write the header
        List<String> header = new ArrayList<>();
        header.add("Student Name");
        header.add("BUID");
        List<Assignment> assignments = gc.getAssignments();
        for (Assignment a : assignments) {
            String assignmentFormat = String.format("%s (%d, %d%%) (%s,%s)", a.getName(), a.getMaxGrade(), a.getWeight(), a.getAssignedDate(), a.getDueDate());
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
        csvPrinter.printRecord(Arrays.asList(headers));
        for (String[] row : data) {
            csvPrinter.printRecord(Arrays.asList(row));
        }
        csvPrinter.flush();
        csvPrinter.close();
    }

    public static void writeSemesters(State state) throws IOException{
        String fileOutName = state.getSemestersFilePath();
        System.out.println(fileOutName);
        CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(fileOutName), CSVFormat.DEFAULT);
        List<Semester> semesters = state.getSemesters();
        String[][] data = new String[semesters.size()][3];
        for (int i = 0; i < semesters.size(); i++) {
            Semester sem = semesters.get(i);
            data[i][0] = sem.getSemester();
            data[i][1] = sem.getYear();
            StringBuilder sb = new StringBuilder();
            for (String path : sem.getCoursePathList()) {
                sb.append(path).append(";");
            }
            data[i][2] = sb.toString();
        }
        for (String[] row : data) {
            csvPrinter.printRecord(Arrays.asList(row));
        }
        csvPrinter.flush();
        csvPrinter.close();
    }
}
