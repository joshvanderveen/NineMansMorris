package view;

import com.sun.tools.javac.Main;
import model.board.GameBoardConfig;
import model.board.Intersection;
import model.serialization.FileReadAndWrite;
import view.boardmaker.UIBoardMaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class UIConfigurations {
    private static ArrayList<Intersection> newBoard;

    public static ArrayList<Intersection> createBoard() {
        ArrayList<Intersection> intersections = new ArrayList<>();

        UIBoardMaker boardMaker = new UIBoardMaker(intersections);
        // create text field for file name
        JLabel boardNameLabel = new JLabel("Board Name: ");
        JTextField boardName = new JTextField(20);
        boardMaker.add(boardNameLabel);
        boardMaker.add(boardName);

        int result = JOptionPane.showConfirmDialog(null, boardMaker, "GameBoard Creator", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // check that boardName is not empty
        if (boardName.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please enter a file name.");
        } else {
            // check that boardName is not already taken
            File[] gameboardDirectories = new File("src/boards").listFiles(File::isDirectory);
            for (File file : gameboardDirectories) {
                if (file.getName().equals(boardName.getText())) {
                    JOptionPane.showMessageDialog(null, "File name already taken.");
                    return null;
                }
            }
        }
        // convert boardName to a file name friendly string
        String boardNameString = boardName.getText().replaceAll("[^a-zA-Z0-9]", "");
        System.out.println(boardNameString);
        if (result == JOptionPane.OK_OPTION) {
            // write intersections and relationships to file
            FileReadAndWrite.writeIntersectionsToFile("src/boards/" + boardNameString + "/intersections.json", intersections);
            FileReadAndWrite.writeRelationshipsToFile("src/boards/" + boardNameString + "/paths.json", intersections);
            return intersections;
        } else {
            System.exit(0);
            return null;
        }
    }

    public static String chooseGameBoard() {
        // get os name
        String os = System.getProperty("os.name").toLowerCase();

        // Use forward slash for macOS and Linux
        String filePath;
        if (os.contains("mac") || os.contains("nix") || os.contains("nux")) {
            filePath = "src/boards";
        }
        // Use backslash for Windows
        else if (os.contains("win")) {
            filePath = "src\\boards";
        }
        else {
            // Default to forward slash
            filePath = "src/boards";
        }

        // options when opening game
        String[] options = new String[] {"Choose Existing Gameboard", "Create New Gameboard"};
        JComboBox<String> gameOptions = new JComboBox<>(options);
        JPanel panel = new JPanel();
        panel.add(new JLabel("Choose an option:"));
        panel.add(gameOptions);

        File[] gameboardDirectories = new File(filePath).listFiles(File::isDirectory);

        ArrayList<String> gameboards = new ArrayList<>();

        for (File file : gameboardDirectories) {
            gameboards.add(file.getName());
        }

        JComboBox<ArrayList<String>> gameConfigOptions = new JComboBox(gameboards.toArray());
        JLabel gameConfigOptionsLabel = new JLabel("Choose a gameboard:");
        panel.add(gameConfigOptionsLabel);
        panel.add(gameConfigOptions);

        gameOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameOptions.getSelectedItem().equals("Create New Gameboard")) {
                    gameConfigOptions.setVisible(false);
                    gameConfigOptionsLabel.setVisible(false);
                    newBoard = UIConfigurations.createBoard();
//                    System.out.println(newBoard);
                } else {
                    gameConfigOptions.setVisible(true);
                    gameConfigOptionsLabel.setVisible(true);
                }
            }
        });

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
