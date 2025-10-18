package form;

import data.EngineerEntity;
import data.EngineerRepository;
import data.ProjectEntity;
import data.ProjectRepository;

import javax.swing.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

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

            refreshEngineersList();

        } catch (Exception e) {
            System.out.println("Ошибка при загрузке данных: " + e.getMessage());
        }

        inputButton.addActionListener(e -> createProject(inputText.getText()));
        searchButton.addActionListener(e -> goToSearchFrame());
        engineersButton.addActionListener(e -> goToEngineersFrame());
        engineerSelect.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                refreshEngineersList();
                super.mouseClicked(e);
            }
        });
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

    private void createProject(String name) {
        boolean found = false;
        for (ProjectEntity project : projectRepository.findAll()) {
            if (project.getName().equalsIgnoreCase(name)) {
                found = true;
                break;
            }
        }
        if (found) {
            JOptionPane.showMessageDialog(this,
                    "Заданное имя проекта уже используется.\n" +
                            "Измените наименование или воспользуйтесь конокой Поиск для редактирования записи");
        } else {
            if (
                    name.trim().isEmpty() ||
                            Objects.equals(engineerSelect.getSelectedItem(), "") ||
                            Objects.equals(engineerSelect.getSelectedItem(), " ")
            ) {
                JOptionPane.showMessageDialog(this,
                        "Необходимо заполнить поля Наименование и Исполнитель" +
                                "\n Для редактирования списка исполнителей, " +
                                "\nвоспользуйтесь кнопкой Изменить список");
            } else {
                ProjectEntity project = new ProjectEntity();
                EngineerEntity engineer;
                engineer = engineerRepository.findEngineerByName(engineerSelect.getSelectedItem().toString());
                project.setEngineerId(engineer);
                project.setName(name);
                project.setDate(LocalDate.now());
                String prefix = (project.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).substring(3, 10);
                projectRepository.save(project);
                int id = 0;
                for (ProjectEntity projectEntity : projectRepository.findAll()) {
                    if (projectEntity.getName().equals(name)) {
                        id = projectEntity.getId();
                    }
                }
                project.setCode(id + "-" + prefix + "-ОВ");
                projectRepository.update(project);
                outputText.setText(project.getCode());
                JOptionPane.showMessageDialog(this,
                        "Проект сохранен.\nНаименование: " + project.getName() +
                                "\nИсполнитель: " + project.getEngineerId().getEngineerName() +
                                "\nДата: " + project.getDate() +
                                "\nПрисвоен шифр: " + project.getCode() +
                        "\nСкопируйте шифр для добавления в документацию");
            }
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

    public void refreshEngineersList(){
        engineerSelect.removeAllItems();
        for(EngineerEntity engineer:engineerRepository.findAllEngineers()){
            engineerSelect.addItem(engineer.getEngineerName());
        }
    }



    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
