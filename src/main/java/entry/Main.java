package entry;

import utils.CSVReader;
import utils.CSVWriter;
import utils.GradedClass;
import utils.Student;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String filePath = "resources/611.csv";
        GradedClass gradedClass = CSVReader.loadCSV(filePath);
        for (Student student : gradedClass.getStudents()) {
            System.out.println(student.getName());
            System.out.println(student.getAssignments());
        }
        CSVWriter.writeGradedClass(gradedClass, "resources/" + gradedClass.getClassName() + "_out.csv");
    }
}
