package form;

import data.ProjectEntity;
import data.ProjectRepository;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class MainFrame extends JDialog {
    private JTextField inputText;
    private JButton inputButton;
    private JTextArea text2;
    private JTextField outputText;
    private JLabel imgLabel;
    private JLabel text1;
    private JPanel contentPane;
    private JComboBox engineerSelect;
    private JButton searchButton;
    private ProjectRepository projectRepository;

    public MainFrame() {
        try {
            setContentPane(contentPane);
            setModal(true);
            String[] engineers = new String[5];
            engineers[0] = "Коновалов С.В.";
            engineers[1] = "Розанцев А.С.";

            IntStream.range(0, 5).forEach(i -> engineerSelect.addItem(engineers[i]));
            projectRepository = new ProjectRepository();

            // Показываем информацию о БД
            showDatabaseInfo();
        } catch (Exception e) {
            System.err.println("ContentPane cannot be set");
        }

        inputButton.addActionListener(e -> createProject(inputText.getText()));
        searchButton.addActionListener(e -> getProject(inputText.getText()));
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
        project.setEngineer(Objects.requireNonNull(engineerSelect.getSelectedItem()).toString());
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
        JOptionPane.showMessageDialog(this,
                "Проект сохранен.\nНаименование: "+project.getName()+
                        "\nИсполнитель: "+project.getEngineer()+
                        "\nДата: "+project.getDate()+
                        "\nПрисвоен шифр: "+project.getCode());
    }

    private void getProject(String name){
        List<ProjectEntity> projects = new ArrayList<>();
        StringBuilder searchResult = new StringBuilder("Найдены проекты:\n");
        for(ProjectEntity projectEntity : projectRepository.findAll()){
            if(projectEntity.getName().equalsIgnoreCase(name)||
                    projectEntity.getName().toLowerCase().contains(name.toLowerCase())){
                projects.add(projectEntity);
            }
        }
        if(projects.isEmpty()){
            JOptionPane.showMessageDialog(this, "Проекты с заданными параметрами не найдены");
            return;
        }
        else {
            for (ProjectEntity foundProject : projects) {
                searchResult.append(foundProject.toString()).append("\n");
            }
            JOptionPane.showMessageDialog(this, searchResult.toString());
        }
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
