package utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVReader {
    private final String fileName;
    private static final int STUDENT_NAME_INDEX = 0;
    private static final int BUID_INDEX = 1;

    public CSVReader(String fileName) {
        this.fileName = fileName;
    }


    private List<CSVRecord> parse() throws IOException {
        Reader in = new FileReader(fileName);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);
        List<CSVRecord> list = new ArrayList<>();
        for (CSVRecord record : records) {
            list.add(record);
        }
        return list;
    }

    public static GradedClass loadCSV(String filePath) throws IOException {
        CSVReader csvReader = new CSVReader(filePath);
        List<CSVRecord> records = csvReader.parse();
        List<Student> students = new ArrayList<>();
        List<Assignment> assignments = new ArrayList<>();
        for (CSVRecord record : records) {
            if (!record.get(STUDENT_NAME_INDEX).equals("Student Name")) {
                Student student = new Student(record.get(STUDENT_NAME_INDEX), Integer.parseInt(record.get(BUID_INDEX)));
                for (int i = 2; i < record.size(); i++) {
                    Assignment assignment = assignments.get(i - 2);
                    assignment.setGrade(Integer.parseInt(record.get(i)));
                    student.addAssignment(assignment);
                }
                students.add(student);
            } else {
                for (int i = 2; i < record.size(); i++) {
                    String[] seperated = record.get(i).split(" ", 2);
                    String assignmentName = seperated[0];

                    List<String> percents = Arrays.stream(seperated[1].substring(1, seperated[1].length() - 1).split(",", 2)).toList();
                    int maxGrade = Integer.parseInt(percents.get(0));
                    int weight = Integer.parseInt(percents.get(1).substring(1, percents.get(1).length() - 1));
                    Assignment assignment = new Assignment(assignmentName, weight, maxGrade, 0);
                    assignments.add(assignment);
                }
            }
        }
        return new GradedClass(students, assignments);
    }
}
