package entry;

import gui.MainFrame;

public class Main {
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setContentPane(mainFrame.panelContainer);
        mainFrame.setBounds(300, 300, 800, 600);
        mainFrame.setVisible(true);
    }
}

