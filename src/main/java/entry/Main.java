package entry;

import gui.MainFrame;
import utils.CSVReader;
import utils.CSVWriter;
import utils.GradedClass;
import utils.Student;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String filePath = "resources/grade.csv";
        GradedClass gradedClass = CSVReader.loadCSV(filePath);
        for (Student student : gradedClass.getStudents()) {
            System.out.println(student.getName());
            System.out.println(student.getAssignments());
        }
        CSVWriter.writeGradedClass(gradedClass, "resources/grade_out.csv");
        MainFrame mainFrame = new MainFrame();
        mainFrame.setContentPane(mainFrame.panelContainer);
        mainFrame.setBounds(300, 300, 800, 600);
        mainFrame.setVisible(true);

    }
}

