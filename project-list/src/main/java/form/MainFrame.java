package form;

import data.ProjectEntity;
import data.ProjectRepository;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MainFrame extends JDialog {
    private JTextField inputText;
    private JButton inputButton;
    private JTextArea text2;
    private JTextField outputText;
    private JLabel imgLabel;
    private JLabel text1;
    private JPanel contentPane;
    private ProjectRepository projectRepository;

    public MainFrame() {
        try {
            setContentPane(contentPane);
            setModal(true);
            projectRepository.findAll();
        } catch (Exception e) {
            System.err.println("ContentPane cannot be set");
        }

        inputButton.addActionListener(e -> createProject(inputText.getText()));
    }

    private void createProject(String name){
        ProjectEntity project = new ProjectEntity();
        project.setName(name);
        project.setDate(LocalDate.now());
        String prefix = (project.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).substring(3,10);
        projectRepository.save(project);
        int id = 0;
        for(ProjectEntity projectEntity : projectRepository.findAll()){
            if(projectEntity.getName().equals(name)){
                id = projectEntity.getId();
            }
        }
        project.setCode(id + "-" + prefix + "-ОВ");
        outputText.setText(project.getCode());
    }


    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
