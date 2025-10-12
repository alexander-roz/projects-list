package form;

import data.EngineerEntity;
import data.EngineerRepository;
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
            showDatabaseInfo();

            engineerSelect.removeAllItems();
            for(EngineerEntity engineer:engineerRepository.findAllEngineers()){
                engineerSelect.addItem(engineer.getEngineerName());
            }

        } catch (Exception e) {
            System.out.println("Ошибка при загрузке данных: " + e.getMessage());
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
                    "База данных H2 подключена успешно!" +
                            "\nНайдено проектов в базе: " + projectCount +
                            "\nНайдено пользователей: " + engineersCount +
                            "\nФайл базы данных: ./database/projects.mv.db",
                    "Информация о БД", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            System.out.println("Ошибка при получении информации о БД: " + e.getMessage());
        }
    }

    private void createProject(String name){
        ProjectEntity project = new ProjectEntity();
        EngineerEntity engineer;
        if(engineerSelect.getSelectedItem()!=null && name!=null){
            engineer = engineerRepository.findEngineerByName(engineerSelect.getSelectedItem().toString());
            project.setEngineerId(engineer);
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
            JOptionPane.showMessageDialog(this,
                    "Проект сохранен.\nНаименование: "+project.getName()+
                            "\nИсполнитель: "+project.getEngineerId().getEngineerName()+
                            "\nДата: "+project.getDate()+
                            "\nПрисвоен шифр: "+project.getCode());
        }
        else {
            JOptionPane.showMessageDialog(this,
                    "Необходимо заполнить поля Наименование и Исполнитель" +
                            "\nЕсли данные исполнителя в базе отсутствуют, воспользуйтесь кнопкой Изменить");
        }
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
