package form;

import data.EngineerEntity;
import data.EngineerRepository;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class EngineersForm  extends JFrame {
    private JPanel contentPaneEF;
    private JLabel imgLabel;
    private JTextField engineerNameEF;
    private JButton updateButtonEF;
    private JComboBox engineerSelectEF;
    private JButton addButtonEF;
    private EngineerRepository engineerRepository;
    private EngineerEntity currentEngineer;

    public EngineersForm () {
        setContentPane(contentPaneEF);
        engineerRepository = new EngineerRepository();
        getEngineers();
        if(!engineerRepository.findAllEngineers().isEmpty()){
            updateButtonEF.setEnabled(true);
        }
        addButtonEF.setEnabled(true);
        addButtonEF.addActionListener(e -> {saveEngineer(engineerNameEF.getText());});
        updateButtonEF.addActionListener(e ->
        {updateEngineer((engineerSelectEF.getSelectedItem().toString()), engineerNameEF.getText());});
    }

    private void getEngineers() {
        List<String> engineers = new ArrayList<>();
        engineerSelectEF.removeAllItems();
        engineerNameEF.setText(null);

        for(EngineerEntity engineer:engineerRepository.findAllEngineers()){
            engineers.add(engineer.getEngineerName());
            engineerSelectEF.addItem(engineer.getEngineerName());
        }

        if(engineers.isEmpty()){
            JOptionPane.showMessageDialog(this, "Исполнители не найдены");
            addButtonEF.setEnabled(true);
            updateButtonEF.setEnabled(false);
            return;
        }
        else {
            updateButtonEF.setEnabled(true);
            addButtonEF.setEnabled(true);
        }
    }

    private void saveEngineer(String engineerName) {
        if (!engineerRepository.findAllEngineers().isEmpty() &&
                engineerRepository.findEngineerByName(engineerName) != null) {
            JOptionPane.showMessageDialog(this, "Данное имя уже используется");
            getEngineers();
        } else {
            EngineerEntity newEngineer = new EngineerEntity();
            newEngineer.setEngineerName(engineerName);
            engineerRepository.saveEngineer(newEngineer);
            JOptionPane.showMessageDialog(this,
                    "Пользователь сохранен.\nПрисвоено имя: " + engineerName);
            getEngineers();
        }
    }

    private void deleteEngineer(String engineerName) {
        EngineerEntity engineer = engineerRepository.findEngineerByName(engineerName);
        engineerRepository.deleteEngineer(engineer.getEngineerId());
        JOptionPane.showMessageDialog(this,
                "Исполнитель: " + engineer.getEngineerName() + " удален из базы данных");
        getEngineers();
    }

    private void updateEngineer(String selectedEngineerName, String refactoringName) {
        EngineerEntity engineer = engineerRepository.findEngineerByName(selectedEngineerName);
        engineer.setEngineerName(refactoringName);
        engineerRepository.updateEngineer(engineer);
        JOptionPane.showMessageDialog(this,
                "Исполнитель: " + engineer.getEngineerName() + ". Запись обновлена");
        getEngineers();
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPaneEF;
    }
}
