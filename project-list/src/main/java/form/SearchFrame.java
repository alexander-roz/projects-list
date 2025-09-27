package form;

import data.ProjectEntity;
import data.ProjectRepository;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.swing.*;
import java.util.*;
import java.util.stream.IntStream;

public class SearchFrame extends JFrame{
    private JPanel contentPane;
    private JLabel imgLabel;
    private JTextField inputTextSF;
    private JComboBox projectsComboBox;
    private JButton searchButtonSF;
    private JButton changeButton;
    private JButton deleteButton;
    private JComboBox engineerSelectSF;
    private JTextField projectNameSF;
    private JTextField codeNameSF;
    private ProjectRepository projectRepository;
    private ProjectEntity currentProject;

    public SearchFrame() {
        try {
            setContentPane(contentPane);
            projectRepository = new ProjectRepository();
        } catch (Exception e) {
            System.err.println("ContentPane cannot be set");
        }
        changeButton.setEnabled(false);
        deleteButton.setEnabled(false);
        searchButtonSF.addActionListener(e -> getProjects(inputTextSF.getText()));
        projectsComboBox.addActionListener(e -> {
            String tmpProjectName = projectsComboBox.getSelectedItem().toString();
            System.out.println(tmpProjectName);
            currentProject = getCurrentProject(tmpProjectName);
        });
        changeButton.addActionListener(e -> {changeProject(currentProject);});
        deleteButton.addActionListener(e -> {deleteProject(currentProject);});
    }

    private void getProjects(String name){
        List<ProjectEntity> projects = new ArrayList<>();
        projectsComboBox.removeAllItems();
        engineerSelectSF.removeAllItems();
        String[] engineers = new String[3];
        engineers[0] = "Коновалов С.В.";
        engineers[1] = "Розанцев А.С.";
        IntStream.range(0, 3).forEach(i -> engineerSelectSF.addItem(engineers[i]));
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
                projectsComboBox.addItem(foundProject.getId() + " " + foundProject.getName());
            }
        }
    }

    private ProjectEntity getCurrentProject(String projectName){
        if(projectName.isEmpty()){
            JOptionPane.showMessageDialog(this, "Данные для запроса отсутствуют");
            return null;
        }
            else {
                Optional<ProjectEntity> projectEntity;
                String[] substring = projectName.split(" ");
                long projectId = Long.parseLong(substring[0]);
                System.out.println("projectId: " + projectId);
                projectEntity = projectRepository.findById(projectId);
                if(projectEntity.isEmpty()){
                    JOptionPane.showMessageDialog(this, "Проекты с заданными параметрами не найдены");
                return null;
            }
            else {
                    projectNameSF.setText(projectEntity.get().getName());
                    codeNameSF.setText(projectEntity.get().getCode());
                    engineerSelectSF.setSelectedItem(projectEntity.get().getEngineer());
                    changeButton.setEnabled(true);
                    deleteButton.setEnabled(true);
                    return projectEntity.get();
                }
        }
    }

    private void changeProject(ProjectEntity project){
        if(project == null){
            JOptionPane.showMessageDialog(this, "Данные для запроса отсутствуют");
        }
        else {
            project.setName(projectNameSF.getText());
            project.setCode(codeNameSF.getText());
            project.setEngineer(engineerSelectSF.getSelectedItem().toString());
            projectRepository.update(project);
            JOptionPane.showMessageDialog(this,
                    "Запись для проекта id " + project.getId() + " обновлена.\n" +
                            "Наименование: " + project.getName() +
                            "\nШифр: " + project.getCode() +
                            "\nИсполнитель: " + project.getEngineer());
            refreshFrame();
        }
    }

    private void deleteProject(ProjectEntity project){
        if(project == null){
            JOptionPane.showMessageDialog(this, "Данные для запроса отсутствуют");
        }
        else {
            projectRepository.delete(project.getId());
            JOptionPane.showMessageDialog(this,
                    "Запись для проекта id " + project.getId() + " удалена.\n" +
                            "Наименование: " + project.getName() +
                            "\nШифр: " + project.getCode() +
                            "\nИсполнитель: " + project.getEngineer());
        refreshFrame();
        }
    }

    private void refreshFrame(){
        projectNameSF.setText("");
        codeNameSF.setText("");
        changeButton.setEnabled(false);
        deleteButton.setEnabled(false);
        getProjects(inputTextSF.getText());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
