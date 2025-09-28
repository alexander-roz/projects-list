package form;

import data.EngineerEntity;
import data.EngineerRepository;
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
    private JButton engineersButton;
    private ProjectRepository projectRepository;
    private EngineerRepository engineerRepository;

    public MainFrame() {
        try {
            setContentPane(contentPane);
            setModal(true);

            projectRepository = new ProjectRepository();
            engineerRepository = new EngineerRepository();
//            String[] engineers = new String[3];
//            engineers[0] = "Коновалов С.В.";
//            engineers[1] = "Розанцев А.С.";
//
//            IntStream.range(0, 3).forEach(i -> engineerSelect.addItem(engineers[i]));
            
            ArrayList <String> engineers = new ArrayList<>();
            for(EngineerEntity engineer:engineerRepository.findAllEngineers()){
                engineers.add(engineer.getName());
            }
            IntStream.range(0, engineers.size()).forEach(i -> {engineerSelect.addItem(engineers.get(i));});
            // Показываем информацию о БД
            showDatabaseInfo();
        } catch (Exception e) {
            System.err.println("ContentPane cannot be set");
        }

        inputButton.addActionListener(e -> createProject(inputText.getText()));
        searchButton.addActionListener(e -> goToSearchFrame());
        engineersButton.addActionListener(e -> goToEngineersFrame());
    }

    private void showDatabaseInfo() {
        try {
            int projectCount = projectRepository.findAll().size();
            int engineersCount = engineerRepository.findAllEngineers().size();
            JOptionPane.showMessageDialog(this,
                    "База данных H2 подключена успешно!\n" +
                            "Найдено проектов в базе: " + projectCount +
                            "Найдено пользователей: " + engineersCount +
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
        project.setEngineerID(engineerRepository.findEngineerByName(engineerSelect.getSelectedItem().toString()));
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
                        "\nИсполнитель: "+project.getEngineerID().getName()+
                        "\nДата: "+project.getDate()+
                        "\nПрисвоен шифр: "+project.getCode());
    }


    private void goToSearchFrame() {
        SearchFrame searchFrame = new SearchFrame();
        searchFrame.pack();
        searchFrame.setLocationRelativeTo(null);
        searchFrame.setVisible(true);
    }

    private void goToEngineersFrame() {
        EngineersForm engineersForm = new EngineersForm();
        engineersForm.pack();
        engineersForm.setLocationRelativeTo(null);
        engineersForm.setVisible(true);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
