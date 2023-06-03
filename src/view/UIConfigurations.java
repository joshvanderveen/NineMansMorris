package view;

import model.board.Intersection;
import model.serialization.FileReadAndWrite;
import view.boardmaker.UIBoardMaker;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class UIConfigurations {

    private static String selectedBoard;

    public static String createBoard() {
        ArrayList<Intersection> intersections = new ArrayList<>();

        UIBoardMaker boardMaker = new UIBoardMaker(intersections);
        // create text field for file name
        JLabel boardNameLabel = new JLabel("Board Name: ");
        JTextField boardName = new JTextField(20);
        boardMaker.add(boardNameLabel);
        boardMaker.add(boardName);

        int result = JOptionPane.showConfirmDialog(null, boardMaker, "GameBoard Creator", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // convert boardName to a file name friendly string
        String boardNameString = boardName.getText().replaceAll("[^a-zA-Z0-9]", "");
        System.out.println(boardNameString);
        if (result == JOptionPane.OK_OPTION) {
            // check that boardName is not empty
            if (boardName.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter a file name.");
                return null;
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
            // get os name
            String os = System.getProperty("os.name").toLowerCase();

            // Use forward slash for macOS and Linux
            String filePath;
            String fileSeparator;
            if (os.contains("mac") || os.contains("nix") || os.contains("nux")) {
                filePath = "src/boards/";
                fileSeparator = "/";
            }
            // Use backslash for Windows
            else if (os.contains("win")) {
                filePath = "src\\boards\\";
                fileSeparator = "\\";
            }
            else {
                // Default to forward slash
                filePath = "src/boards/";
                fileSeparator = "/";
            }

            // write intersections and relationships to file
            FileReadAndWrite.writeIntersectionsToFile(filePath + boardNameString + fileSeparator + "intersections.json", intersections);
            FileReadAndWrite.writeRelationshipsToFile(filePath + boardNameString + fileSeparator + "paths.json", intersections);
//            return intersections;
            return boardNameString;
        } else {
            return null;
        }
    }

    public static String chooseGameBoard() {
        // get os name
        String os = System.getProperty("os.name").toLowerCase();

        // Use forward slash for macOS and Linux
        String filePath = os.contains("win") ? "src\\boards" : "src/boards";

        // options when opening game
        UIOpenGameboardOptions[] options = {UIOpenGameboardOptions.EXISTING_BOARD, UIOpenGameboardOptions.NEW_BOARD};

        JComboBox<UIOpenGameboardOptions> gameOptions = new JComboBox<>(options);


        JPanel panel = new JPanel();
        panel.add(new JLabel("Choose an option:"));
        panel.add(gameOptions);

        File[] gameboardDirectories = new File(filePath).listFiles(File::isDirectory);

        ArrayList<String> gameboards = new ArrayList<>();

        for (File file : gameboardDirectories) {
            gameboards.add(file.getName());
        }

        JComboBox<ArrayList<String>> gameBoardOptions = new JComboBox(gameboards.toArray());
        JLabel gameBoardOptionsLabel = new JLabel("Choose a gameboard:");
        panel.add(gameBoardOptionsLabel);
        panel.add(gameBoardOptions);

        gameOptions.addActionListener(e -> {
            switch ((UIOpenGameboardOptions) Objects.requireNonNull(gameOptions.getSelectedItem())) {
                case NEW_BOARD -> {
                    gameBoardOptions.setVisible(false);
                    gameBoardOptionsLabel.setVisible(false);
                    selectedBoard = UIConfigurations.createBoard();
                }

                case EXISTING_BOARD -> {
                    gameBoardOptions.setVisible(true);
                    gameBoardOptionsLabel.setVisible(true);
                }
            }
        });
        // TODO: Update the dropdown list to add selectedBoard

        int result = JOptionPane.showConfirmDialog(null, panel, "Game Configuration", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            if (selectedBoard != null) return selectedBoard;
            return (String) gameBoardOptions.getSelectedItem();
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
