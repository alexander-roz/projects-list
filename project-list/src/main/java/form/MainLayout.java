package form;

import javax.swing.*;

public class MainLayout extends JDialog {
    private JTextField inputText;
    private JButton inputButton;
    private JTextArea text2;
    private JTextField outputText;
    private JLabel imgLabel;
    private JLabel text1;
    private JPanel contentPane;

    public MainLayout() {
        try {
            setContentPane(contentPane);
            setModal(true);
        }
        catch (Exception e) {
            System.err.println("ContentPane cannot be set");
        }

    }
    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
