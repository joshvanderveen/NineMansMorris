package view;

import com.sun.tools.javac.Main;
import model.board.GameBoardConfig;
import model.board.Intersection;
import view.boardmaker.UIBoardMaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class UIConfigurations {

    public static ArrayList<Intersection> createBoard() {
        ArrayList<Intersection> intersections = new ArrayList<>();

        UIBoardMaker boardMaker = new UIBoardMaker(intersections);

        int result = JOptionPane.showConfirmDialog(null, boardMaker, "GameBoard Creator", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            return intersections;
        } else {
            System.exit(0);
            return null;
        }
    }

    public static String chooseGameBoard() {
        File[] gameboardDirectories = new File("src\\boards").listFiles(File::isDirectory);

        ArrayList<String> gameboards = new ArrayList<>();

        for (File file : gameboardDirectories) {
            gameboards.add(file.getName());
        }

        JComboBox<ArrayList<String>> gameConfigOptions = new JComboBox(gameboards.toArray());

        JPanel panel = new JPanel();
        panel.add(new JLabel("Choose a gameboard:"));
        panel.add(gameConfigOptions);

        int result = JOptionPane.showConfirmDialog(null, panel, "Game Configuration", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            return (String) gameConfigOptions.getSelectedItem();
        } else {
            System.exit(0);
            return null;
        }
    }

    public static ArrayList<String> choosePlayerNames() {
        ArrayList<String> playerNames = new ArrayList<>();
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Enter player names:"));

        JTextField textField = new JTextField(10);

        panel.add(textField);

        JButton addButton = new JButton("Add");
        
        DefaultListModel listModel = new DefaultListModel();


        addButton.addActionListener(e -> {
            String name = textField.getText();
            if (name.length() > 0) {
                playerNames.add(name);
                listModel.addElement(name);
                textField.setText("");
                textField.grabFocus();
            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.add(textField);
        inputPanel.add(addButton);

        panel.add(inputPanel);

        JList scrollPaneList = new JList<>(listModel);

        panel.add(new JScrollPane(scrollPaneList));

        panel.setVisible(true);

        int result = JOptionPane.showConfirmDialog(null, panel, "Game Configuration", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            return playerNames;
        } else {
            System.exit(0);
            return null;
        }
    }

    public static Integer chooseMillLength() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Choose a mill length:"));

        String response = JOptionPane.showInputDialog(null, panel, "Choose a mill length", JOptionPane.OK_CANCEL_OPTION);

        try {
            return Integer.parseInt(response);
        } catch (Exception e) {
            System.exit(0);
            return null;
        }
    }
}
