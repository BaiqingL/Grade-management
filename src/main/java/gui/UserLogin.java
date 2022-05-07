package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserLogin extends JPanel {
    private JPanel loginPanel;
    private JPanel title;
    private JButton loginButton;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JLabel projectName;
    private JLabel invalidPasswordWarning;
    private JPanel loginForm;

    protected boolean isAuthenticated;

    public UserLogin() {

        this.isAuthenticated = false;

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (authenticate(String.valueOf(passwordField.getPassword()))) {
                    System.out.println("logged in");
                    passwordField.setText("");
                    invalidPasswordWarning.setVisible(false);
                } else {
                    invalidPasswordWarning.setVisible(true);
                }
            }
        });
    }

    private boolean authenticate(String password) {
        // validate the password for service access
        String validPassword = "password";
        System.out.println(password);
        firePropertyChange("isAuthenticated", this.isAuthenticated, validPassword.equals(password));
        this.isAuthenticated = validPassword.equals(password);
        return this.isAuthenticated;
    }

    public void setLogout() {
        this.isAuthenticated = false;
    }

}
