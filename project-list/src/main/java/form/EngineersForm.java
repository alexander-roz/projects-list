package form;

import data.EngineerEntity;
import data.EngineerRepository;
import data.ProjectRepository;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class EngineersForm  extends JFrame {
    private JPanel contentPaneEF;
    private JLabel imgLabel;
    private JTextField engineerNameEF;
    private JButton changeButtonEF;
    private JButton deleteButtonEF;
    private JComboBox engineerSelectEF;
    private JButton addButtonEF;
    private EngineerRepository engineerRepository;
    private EngineerEntity currentEngineer;

    public EngineersForm () {
        setContentPane(contentPaneEF);
        engineerRepository = new EngineerRepository();
        getEngineers();
        if(!engineerRepository.findAllEngineers().isEmpty()){
            changeButtonEF.setEnabled(true);
            deleteButtonEF.setEnabled(true);
        }
        addButtonEF.setEnabled(true);
        addButtonEF.addActionListener(e -> {saveEngineer(engineerNameEF.getText());});
        deleteButtonEF.addActionListener(e -> {deleteEngineer(engineerSelectEF.getSelectedItem().toString());});

    }

    private void getEngineers() {
        List<String> engineers = new ArrayList<>();
        engineerSelectEF.removeAllItems();
        engineerNameEF.setText(null);
        for(EngineerEntity engineer:engineerRepository.findAllEngineers()){
            engineers.add(engineer.getName());
            engineerSelectEF.addItem(engineer.getName());
        }
        if(engineers.isEmpty()){
            JOptionPane.showMessageDialog(this, "Исполнители не найдены");
            addButtonEF.setEnabled(true);
            return;
        }
        else {
            changeButtonEF.setEnabled(true);
            deleteButtonEF.setEnabled(true);
            addButtonEF.setEnabled(true);
        }
    }

    private void saveEngineer(String engineerName) {
        if(engineerName == null || engineerName.isEmpty()){
            JOptionPane.showMessageDialog(this, "Для добавления записи необходимо заполнить имя");
        return;
        }

        else {
            EngineerEntity newEngineer = new EngineerEntity();
            newEngineer.setName(engineerName);
            for (EngineerEntity tmpEng:engineerRepository.findAllEngineers()){
                if(tmpEng.getName().equals(newEngineer.getName())){
                    JOptionPane.showMessageDialog(this, "Данное имя уже используется");
                    return;
                }
                else {
                    engineerRepository.saveEngineer(newEngineer);
                    JOptionPane.showMessageDialog(this,
                            "Пользователь сохранен.\nНаименование: " + newEngineer.getName());
                    getEngineers();
                }
            }

        }
    }

    private void deleteEngineer(String engineerName) {
        EngineerEntity engineer = engineerRepository.findEngineerByName(engineerName);
        engineerRepository.deleteEngineer(engineer.getId());
        JOptionPane.showMessageDialog(this, "Исполнитель: " + engineer.getName() + " удален из базы данных");
        getEngineers();
    }



    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPaneEF;
    }
}
