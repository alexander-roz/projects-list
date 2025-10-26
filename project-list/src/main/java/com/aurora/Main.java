package com.aurora;

import form.MainFrame;

import javax.swing.*;

public class Main extends JFrame {
    public static void main(String[] args) {
        MainFrame container = new MainFrame();
        JFrame frame = new JFrame("Project list");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 450);
        frame.setLocationRelativeTo(null);
        frame.add(container.getContentPane());
        frame.setVisible(true);
    }
}

/**TODO
 * добавить кнопку вывода списка проектов в файл
 * добавить проверки в методы
 * исправить ошибку с добавлением пустого проекта в БД
 * протестировать на возможные ошибки
 * подкорректировать дизайн (дополнительно)
 * */