package form;

import data.entities.ProjectEntity;
import data.repositories.ProjectEntityRepository;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.swing.*;
import java.util.List;

public class MainLayout extends JDialog {
    private JTextField inputText;
    private JButton inputButton;
    private JTextArea text2;
    private JTextField outputText;
    private JLabel imgLabel;
    private JLabel text1;
    private JPanel contentPane;
    SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    ProjectEntityRepository projectDao = new ProjectEntityRepository(sessionFactory);

    public MainLayout() {
        try {
            setContentPane(contentPane);
            setModal(true);
        } catch (Exception e) {
            System.err.println("ContentPane cannot be set");
        }

    }

    // Добавление проекта
    private String createProject(String name) {
        ProjectEntity project = new ProjectEntity();
        project.setName(name);
        projectDao.save(project);
        return project.getCode();
    }

    // Получение всех проектов
    private List<ProjectEntity> getProjects() {
        List<ProjectEntity> projects = projectDao.findAll();
        projectDao.close();
        return projects;
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
