package com.aurora;

import form.MainLayout;

import javax.swing.*;
import java.awt.*;

import static java.awt.Toolkit.getDefaultToolkit;

public class Main extends JFrame {
    public static void main(String[] args) {
        MainLayout container = new MainLayout();
        JFrame frame = new JFrame("Project List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 350);
        frame.setLocationRelativeTo(null);
        frame.add(container.getContentPane());
        frame.setVisible(true);
    }
}