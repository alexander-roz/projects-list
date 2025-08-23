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
            projectRepository = new ProjectRepository();

            // Показываем информацию о БД
            showDatabaseInfo();
        } catch (Exception e) {
            System.err.println("ContentPane cannot be set");
        }

        inputButton.addActionListener(e -> createProject(inputText.getText()));
    }

    private void showDatabaseInfo() {
        try {
            int projectCount = projectRepository.findAll().size();
            JOptionPane.showMessageDialog(this,
                    "База данных H2 подключена успешно!\n" +
                            "Найдено проектов в базе: " + projectCount +
                            "\nФайл базы данных: ./database/projects.mv.db",
                    "Информация о БД", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            System.out.println("Ошибка при получении информации о БД: " + e.getMessage());
        }
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
        projectRepository.update(project);
        outputText.setText(project.getCode());
    }


    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
