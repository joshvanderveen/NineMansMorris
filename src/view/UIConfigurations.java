package view;

import model.board.Intersection;
import model.serialization.FileReadAndWrite;
import view.boardmaker.UIBoardMaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class UIConfigurations {

    private static String selectedBoard;
    private static JComboBox<ArrayList<String>> gameBoardOptions;
    private static ArrayList<String> gameboards = new ArrayList<>();

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
            String filePath = os.contains("win") ? "src\\boards\\" : "src/boards/";
            String fileSeparator = os.contains("win") ? "\\" : "/";

            // write intersections and relationships to file
            FileReadAndWrite.writeIntersectionsToFile(filePath + boardNameString + fileSeparator + "intersections.json", intersections);
            FileReadAndWrite.writeRelationshipsToFile(filePath + boardNameString + fileSeparator + "paths.json", intersections);
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
        String fileSeparator = os.contains("win") ? "\\" : "/";

        // options when opening game
        UIOpenGameboardOptions[] options = {UIOpenGameboardOptions.EXISTING_BOARD, UIOpenGameboardOptions.NEW_BOARD, UIOpenGameboardOptions.DELETE_BOARD};

        JComboBox<UIOpenGameboardOptions> gameOptions = new JComboBox<>(options);


        JPanel panel = new JPanel();
        panel.add(new JLabel("Choose an option:"));
        panel.add(gameOptions);

        File[] gameboardDirectories = new File(filePath).listFiles(File::isDirectory);

        gameboards = new ArrayList<>();

        for (File file : gameboardDirectories) {
            gameboards.add(file.getName());
        }

        gameBoardOptions = new JComboBox(gameboards.toArray());
        JLabel gameBoardOptionsLabel = new JLabel("Choose a gameboard:");
        panel.add(gameBoardOptionsLabel);
        panel.add(gameBoardOptions);


        gameOptions.addActionListener(e -> {
            switch ((UIOpenGameboardOptions) Objects.requireNonNull(gameOptions.getSelectedItem())) {
                case NEW_BOARD -> {
                    gameBoardOptions.setVisible(false);
                    gameBoardOptionsLabel.setVisible(false);
                    selectedBoard = UIConfigurations.createBoard();

                    // after board is created we want to show the dropdown again and reload the items in the dropdown
                    reloadBoards(filePath, panel);
                    gameOptions.setSelectedItem(UIOpenGameboardOptions.EXISTING_BOARD);
                    gameBoardOptions.setVisible(true);
                    gameBoardOptionsLabel.setVisible(true);
                    // get the index of the newly created board and set it as the selected item
                    gameBoardOptions.setSelectedIndex(gameboards.indexOf(selectedBoard));
                }

                case EXISTING_BOARD -> {
                    gameBoardOptions.setVisible(true);
                    gameBoardOptionsLabel.setVisible(true);
                }

                case DELETE_BOARD -> {
                    gameBoardOptions.setVisible(false);
                    gameBoardOptionsLabel.setVisible(false);

                    String boardToDelete = (String) gameBoardOptions.getSelectedItem();
                    String fileToDelete = filePath + fileSeparator + boardToDelete;
                    // show confirmation popup
                    int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + boardToDelete + "?", "Delete Board", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
                    // check result and delete if yes
                    if (result == JOptionPane.YES_OPTION)
                        FileReadAndWrite.deleteBoard(fileToDelete);
                    // reload boards
                    reloadBoards(filePath, panel);
                    gameOptions.setSelectedItem(UIOpenGameboardOptions.EXISTING_BOARD);
                    gameBoardOptions.setVisible(true);
                    gameBoardOptionsLabel.setVisible(true);
                }
            }
        });

        int result = JOptionPane.showConfirmDialog(null, panel, "Game Configuration", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            if (selectedBoard != null) return selectedBoard;
            return (String) gameBoardOptions.getSelectedItem();
        } else {
            System.exit(0);
            return null;
        }
    }

    private static void reloadBoards(String filePath, JPanel panel) {
        File[] gameboardDirectories = new File(filePath).listFiles(File::isDirectory);

        gameboards = new ArrayList<>();

        for (File file : gameboardDirectories) {
            gameboards.add(file.getName());
        }

        // load in new board
        gameBoardOptions = new JComboBox(gameboards.toArray());
        panel.add(gameBoardOptions);
        panel.repaint();
    }

    public static ArrayList<String> choosePlayerNames() {
        ArrayList<String> playerNames = new ArrayList<>();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelPanel.add(new JLabel("Enter player names:"));
        panel.add(labelPanel);

        JTextField textField = new JTextField(10);
        panel.add(textField);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> scrollPaneList = new JList<>(listModel);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            String name = textField.getText();
            if (name.length() > 0) {
                playerNames.add(name);
                listModel.addElement(name);
                textField.setText("");
                textField.grabFocus();
            }
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> {
            int selectedIndex = scrollPaneList.getSelectedIndex();
            if (selectedIndex != -1) {
                playerNames.remove(selectedIndex);
                listModel.remove(selectedIndex);
            }
        });

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(textField);
        inputPanel.add(addButton);
        inputPanel.add(deleteButton);
        panel.add(inputPanel);

        panel.add(new JScrollPane(scrollPaneList));

        panel.setVisible(true);

        while (playerNames.size() < 2) {
            int result = JOptionPane.showConfirmDialog(null, panel, "Game Configuration",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result != JOptionPane.OK_OPTION) {
                System.exit(0);
            }

            if (playerNames.size() < 2) JOptionPane.showMessageDialog(null, "Please enter at least two player names.");
        }

        return playerNames;
    }

    public static Integer chooseMillLength() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Choose a mill length:"));

        String response;
        do {
            response = (String) JOptionPane.showInputDialog(null, panel, "Choose a mill length", JOptionPane.OK_CANCEL_OPTION, null, null, "3");

            if (response == null) {
                System.exit(0);
                return null;
            }

            try {
                int millLength = Integer.parseInt(response);
                if (millLength <= 1) {
                    JOptionPane.showMessageDialog(null, "Mill length must be greater than 1.");
                    response = null;
                } else {
                    return millLength;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid mill length.");
                response = null;
            }
        } while (response == null);

        return null;
    }

    public static Integer chooseNumberOfPieces(int maxNumber) {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Choose a number of pieces:"));

        String response;
        do {
            response = (String) JOptionPane.showInputDialog(null, panel, "Choose a number of pieces", JOptionPane.OK_CANCEL_OPTION, null, null, "9");

            if (response == null) {
                System.exit(0);
                return null;
            }

            try {
                int numberOfPieces = Integer.parseInt(response);
                if (numberOfPieces <= 0) {
                    JOptionPane.showMessageDialog(null, "Number of pieces must be greater than 0.");
                    response = null;
                } else if (numberOfPieces > maxNumber) {
                    JOptionPane.showMessageDialog(null, "Number of pieces must be less than the amount of intersections");
                    response = null;
                } else {
                    return numberOfPieces;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number of pieces.");
                response = null;
            }
        } while (response == null);

        return null;
    }
}
