package utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.io.Files.getNameWithoutExtension;

public record CSVReader(String fileName) {
    private static final int STUDENT_NAME_INDEX = 0;
    private static final int BUID_INDEX = 1;

    public static GradedClass loadCSV(String filePath) throws IOException {
        CSVReader csvReader = new CSVReader(filePath);
        List<CSVRecord> records = csvReader.parse();
        List<Student> students = new ArrayList<>();
        List<Assignment> assignments = new ArrayList<>();

        // Get header record and parse all the assignments
        CSVRecord header = records.get(0);
        for (int i = 2; i < header.size(); i += 2) {
            String[] parts = header.get(i).split(" ");
            String assignmentName = parts[0];
            int maxPoints = Integer.parseInt(parts[1].replaceAll("[^\\d.]", ""));
            int weight = Integer.parseInt(parts[2].replaceAll("[^\\d.]", ""));
            String[] dateTuple = parts[3].split(",");
            LocalDate assignedDate = LocalDate.parse(dateTuple[0].substring(1));
            LocalDate dueDate = LocalDate.parse(dateTuple[1].substring(0, dateTuple[1].length() - 1));
            assignments.add(new Assignment(assignmentName, weight, maxPoints, 0, assignedDate, dueDate, null));
        }

        // Remove records header and parse all the students
        records.remove(0);

        // Parse all the students
        for (CSVRecord record : records) {
            Student student = new Student(record.get(STUDENT_NAME_INDEX), Integer.parseInt(record.get(BUID_INDEX)));
            int assignmentIndex = 0;
            // Check if the student has any grades for the assignment
            for (int i = 2; i < record.size(); i += 2) {
                Assignment assignment = assignments.get(assignmentIndex);
                Assignment studentAssignment = new Assignment(assignment.getName(), assignment.getWeight(), assignment.getMaxGrade(), Integer.parseInt(record.get(i)), assignment.getAssignedDate(), assignment.getDueDate(), LocalDate.parse(record.get(i + 1)));
                student.addAssignment(studentAssignment);
                // Increment assignment index to check next assignment
                assignmentIndex++;
            }
            // Add student to general list
            students.add(student);
        }

        // Return entire graded class
        String className = getNameWithoutExtension(filePath);
        return new GradedClass(className, students, assignments);
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
}
