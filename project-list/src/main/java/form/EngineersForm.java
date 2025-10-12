package form;

import com.aurora.Main;
import data.EngineerEntity;
import data.EngineerRepository;

import javax.swing.*;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EngineersForm  extends JFrame {
    private JPanel contentPaneEF;
    private JLabel imgLabel;
    private JTextField engineerNameEF;
    private JButton deleteButtonEF;
    private JComboBox engineerSelectEF;
    private JButton addButtonEF;
    private EngineerRepository engineerRepository;
    private EngineerEntity currentEngineer;

    public EngineersForm () {
        setContentPane(contentPaneEF);
        engineerRepository = new EngineerRepository();
        getEngineers();
//        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//        this.addWindowListener(new java.awt.event.WindowAdapter() {
//            @Override
//            public void windowClosing(java.awt.event.WindowEvent e) {
//                MainFrame mainFrame = new MainFrame();
//                mainFrame.refreshEngineersList();
//                e.getWindow().dispose();
//                System.out.println("Engineers form closed!");
//            }
//        });
        if(!engineerRepository.findAllEngineers().isEmpty()){
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
            engineers.add(engineer.getEngineerName());
            engineerSelectEF.addItem(engineer.getEngineerName());
        }

        if(engineers.isEmpty()){
            JOptionPane.showMessageDialog(this, "Исполнители не найдены");
            addButtonEF.setEnabled(true);
            deleteButtonEF.setEnabled(false);
            return;
        }
        else {
            deleteButtonEF.setEnabled(true);
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

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPaneEF;
    }
}
