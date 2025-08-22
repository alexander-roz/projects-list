package form;

import data.ProjectEntity;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MainLayout extends JDialog {
    private JTextField inputText;
    private JButton inputButton;
    private JTextArea text2;
    private JTextField outputText;
    private JLabel imgLabel;
    private JLabel text1;
    private JPanel contentPane;

    public MainLayout() {
        try {
            setContentPane(contentPane);
            setModal(true);
        } catch (Exception e) {
            System.err.println("ContentPane cannot be set");
        }

        inputButton.addActionListener(e -> createProject(inputText.getText()));

    }

    private void createProject(String name){
        ProjectEntity project = new ProjectEntity();
        project.setName(name);
        project.setId(1);
        project.setDate(LocalDate.now());
        String prefix = (project.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).substring(3,10);
        project.setCode(project.getId() + "-" + prefix + "-ОВ");
        outputText.setText(project.getCode());
    }
    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
