import model.serialization.FileReadAndWrite;
import model.Player;
import model.board.*;
import controller.Game;
import view.UIConfigurations;
import view.UIMainGui;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Application {

    private static ArrayList<Player> players = new ArrayList<>();

    /**
     * Main runtime method that is called to start the game
     * @param args default arguments
     */
    public static void main(String[] args) {

        String gameboardName = UIConfigurations.chooseGameBoard();

        String os = System.getProperty("os.name").toLowerCase();
        String fileSeparator = os.contains("win") ? "\\" : "/";
        String gameboardPath = "src" + fileSeparator + "boards" + fileSeparator + gameboardName + fileSeparator;
        String intersectionsFilePath = gameboardPath + "intersections.json";

        ArrayList<Intersection> intersectionsFromFile = FileReadAndWrite.readIntersectionsFromFile(intersectionsFilePath);

        ArrayList<String> playerNames = UIConfigurations.choosePlayerNames();
        Integer millLength = UIConfigurations.chooseMillLength();
        Integer numberOfPieces = UIConfigurations.chooseNumberOfPieces((int) Math.floor(intersectionsFromFile.size() / playerNames.size()));

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
        GameBoard gameBoard = initialiseGameboard(gameboardName, numberOfPieces);

        gameBoard.setMillLength(millLength);

        // View
        UIMainGui gui = new UIMainGui();
        // Controller
        new Game(gameBoard, gui, players);
    }

    /**
     * Initialises the GameBoard based on the game configuration
     *
     * @param gameboardName  the gameboard name to initialise the GameBoard with
     * @param numberOfPieces
     * @return the initialised GameBoard
     */
    public static GameBoard initialiseGameboard(String gameboardName, int numberOfPieces) {



        GameBoard gameBoard = new GameBoard();
        gameBoard.setNumberOfPieces(numberOfPieces);

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

        for (int i = 0; i < gameBoard.getNumberOfPieces(); i++) {
            for (Player player : players) {
                gameBoard.addUnplacedPiece(new Piece(player));
            }
        }

        return gameBoard;
    }

}