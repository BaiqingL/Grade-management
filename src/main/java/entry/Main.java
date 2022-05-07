package entry;

import gui.MainFrame;
import utils.CSVReader;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        CSVReader csvReader = new CSVReader("resources/grade.csv");
        csvReader.parse();
        MainFrame mainFrame = new MainFrame();
        mainFrame.setContentPane(mainFrame.panelContainer);
        mainFrame.setBounds(300, 300, 800, 600);
        mainFrame.setVisible(true);

    }
}

