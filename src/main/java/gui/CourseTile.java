package gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class CourseTile extends JPanel {
    private JButton courseTile;
    private JPanel tilePanel;

    private boolean isSelected;

    private String courseName;

    private int idx;

    public CourseTile(String name, int idx) {
        super();
        this.courseName = name;
        this.idx = idx;
        this.isSelected = false;

        courseTile.setText(this.courseName);

        courseTile.setVisible(true);

        tilePanel.setSize(200, 150);

        tilePanel.setVisible(true);

//        tilePanel.setBackground(Color.BLACK);

        courseTile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("selected course " + courseName);
                setSelected();
            }
        });
    }

    private void setSelected() {

        this.firePropertyChange("isSelected", -1, idx);
        this.isSelected = !this.isSelected;

    }

    public JPanel getTilePanel() {
        return this.tilePanel;
    }

    public String toString() {
        return this.courseName;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        tilePanel = new JPanel();
        tilePanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 30, 30), -1, -1));
        courseTile = new JButton();
        Font courseTileFont = this.$$$getFont$$$("Century Gothic", Font.BOLD, 20, courseTile.getFont());
        if (courseTileFont != null) courseTile.setFont(courseTileFont);
        courseTile.setText("courseName");
        tilePanel.add(courseTile, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return tilePanel;
    }

}
