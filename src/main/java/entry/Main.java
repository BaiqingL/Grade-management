package entry;

import org.apache.commons.csv.CSVRecord;
import utils.Assignment;
import utils.CSVReader;
import utils.GradedClass;
import utils.Student;

import java.io.IOException;

import utils.CSVReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String filePath = "resources/grade.csv";
        GradedClass gradedClass = CSVReader.loadCSV(filePath);
        for (Student student : gradedClass.getStudents()) {
            System.out.println(student.getName());
            System.out.println(student.getAssignments());
        }
    }
}
