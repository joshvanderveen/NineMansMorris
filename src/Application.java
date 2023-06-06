import model.serialization.FileReadAndWrite;
import model.Player;
import model.board.*;
import controller.Game;
import view.UIConfigurations;
import view.UIMainGui;
import view.boardmaker.UIBoardMaker;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Application {

    private static ArrayList<Player> players = new ArrayList<>();

    /**
     * Main runtime method that is called to start the game
     * @param args default arguments
     */
    public static void main(String[] args) {

        String gameBoardName = UIConfigurations.chooseGameBoard();

        ArrayList<String> playerNames = UIConfigurations.choosePlayerNames();
        Integer millLength = UIConfigurations.chooseMillLength();

        ArrayList<Color> colours = new ArrayList<>();

        colours.add(new Color(135, 186, 171));
        colours.add(new Color(90, 210, 244));
        colours.add(new Color(255, 188, 66));
        colours.add(new Color(157, 121, 188));
        colours.add(new Color(247, 175, 157));
        colours.add(new Color(162, 74, 74));
        colours.add(new Color(130, 203, 105));

        for (int i = 0; i < playerNames.size(); i++) {
            players.add(new Player(playerNames.get(i), colours.get(i)));
        }

        // Model
        GameBoard gameBoard = initialiseGameboard(gameBoardName);

        gameBoard.setMillLength(millLength);



        // Examples of how to read and write intersections and relationships

//        FileReadAndWrite.writeIntersectionsToFile("src\\boards\\GAMEBOARD_1\\intersections.json", intersections);

//        FileReadAndWrite.writeRelationshipsToFile("src\\boards\\GAMEBOARD_1\\paths.json", intersections);

//        ArrayList<Intersection> intersectionsFromFile = FileReadAndWrite.readIntersectionsFromFile("src\\boards\\GAMEBOARD_1\\intersections.json");

//        HashMap[] relationshipsFromFile = FileReadAndWrite.readRelationshipsFromFile("src\\boards\\GAMEBOARD_1\\paths.json");

        // View
        UIMainGui gui = new UIMainGui();
        // Controller
        new Game(gameBoard, gui, players);
    }

    /**
     * Initialises the GameBoard based on the game configuration
     * @param gameboardName the gameboard name to initialise the GameBoard with
     * @return the initialised GameBoard
     */
    public static GameBoard initialiseGameboard(String gameboardName) {

        GameBoard gameBoard = new GameBoard();

        String os = System.getProperty("os.name").toLowerCase();
        String fileSeparator = os.contains("win") ? "\\" : "/";

        String gameboardPath = "src" + fileSeparator + "boards" + fileSeparator + gameboardName + fileSeparator;
        String intersectionsFilePath = gameboardPath + "intersections.json";
        String relationshipsFilePath = gameboardPath + "paths.json";

        ArrayList<Intersection> intersectionsFromFile = FileReadAndWrite.readIntersectionsFromFile(intersectionsFilePath);
        HashMap<String, Integer>[] relationshipsFromFile = FileReadAndWrite.readRelationshipsFromFile(relationshipsFilePath);

        for (Intersection intersection : intersectionsFromFile) {
            gameBoard.addIntersection(intersection);
        }

        for (int i = 0; i < relationshipsFromFile.length; i++) {
            Intersection sourceIntersection = null;
            Intersection destinationIntersection = null;

            for (Intersection intersection : intersectionsFromFile) {
                if (intersection.getId() == relationshipsFromFile[i].get("sourceIntersection")) {
                    sourceIntersection = intersection;
                }

                if (intersection.getId() == relationshipsFromFile[i].get("destinationIntersection")) {
                    destinationIntersection = intersection;
                }
            }

            if (sourceIntersection == null || destinationIntersection == null) continue;

            if (!gameBoard.doesPathExist(sourceIntersection, destinationIntersection)) {
                Path newPath = new Path(sourceIntersection, destinationIntersection);

                gameBoard.getIntersectionById(sourceIntersection.getId()).addPath(newPath);
                gameBoard.getIntersectionById(destinationIntersection.getId()).addPath(newPath);
            }
        }

        // TODO: Change to when amount of pieces gets picked
        for (int i = 0; i < 9; i++) {
            for (Player player : players) {
                gameBoard.addUnplacedPiece(new Piece(player));
            }
        }

        return gameBoard;
    }

}