package entry;

import org.apache.commons.csv.CSVRecord;
import utils.Assignment;
import utils.CSVReader;
import utils.GradedClass;
import utils.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static final int STUDENT_NAME_INDEX = 0;
    private static final int BUID_INDEX = 1;

    private static int courseCount = 0;

    public static void main(String[] args) throws IOException {
        GradedClass gradedClass = loadCSV("resources/grade.csv");
    }

    private static GradedClass loadCSV(String filePath) throws IOException {
        CSVReader csvReader = new CSVReader(filePath);
        List<CSVRecord> records = csvReader.parse();
        List<Student> students = new ArrayList<>();
        List<Assignment> assignments = new ArrayList<>();
        for (CSVRecord record : records) {
            if (courseCount == 0) {
                courseCount = record.size() - 2;
            }
            if (!record.get(STUDENT_NAME_INDEX).equals("Student Name")) {
                Student student = new Student(record.get(STUDENT_NAME_INDEX), Integer.parseInt(record.get(BUID_INDEX)));
                students.add(student);
            } else {
                for (int i = 2; i < record.size(); i++) {
                    String[] seperated = record.get(i).split(" ", 2);
                    String assignmentName = seperated[0];

                    List<String> percents = Arrays.stream(seperated[1].substring(1, seperated[1].length() - 1).split(",", 2)).toList();
                    int maxGrade = Integer.parseInt(percents.get(0));
                    int weight = Integer.parseInt(percents.get(1).substring(1, percents.get(1).length() - 1));
                    Assignment assignment = new Assignment(assignmentName, maxGrade, weight, 0);
                    assignments.add(assignment);
                }
            }
        }
        return new GradedClass(students, assignments);
    }
}
