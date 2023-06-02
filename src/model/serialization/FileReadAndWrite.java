package model.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import model.board.GameBoard;
import model.board.Intersection;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public final class FileReadAndWrite {

    /**
     * Write the intersections that make up a gameboard to a file
     * @param filename the name of the file to write to
     * @param intersections the intersections being written to the file
     */
    public static void writeIntersectionsToFile(String filename, List<Intersection> intersections) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Intersection.class, new IntersectionSerializer())
                .setPrettyPrinting()
                .create();

        // Create new directory if it doesn't exist
        File file = new File(filename);
        File parentDirectory = file.getParentFile();
        if (parentDirectory != null && !parentDirectory.exists()) {
            parentDirectory.mkdirs();
        }

        try {
            FileWriter writer = new FileWriter(file);
            writer.write(gson.toJson(intersections));
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Write the relationships that make up a gameboard to a file
     * @param filename the name of the file to write to
     * @param intersections the intersections with the relationships that are being written to the file
     */
    public static void writeRelationshipsToFile(String filename, List<Intersection> intersections) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Intersection.class, new RelationshipsSerializer())
                .setPrettyPrinting()
                .create();

        // Create new directory if it doesn't exist
        File file = new File(filename);
        File parentDirectory = file.getParentFile();
        if (parentDirectory != null && !parentDirectory.exists()) {
            parentDirectory.mkdirs();
        }

        try {
            FileWriter writer = new FileWriter(filename);
            writer.write(gson.toJson(intersections));
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Read the intersections stored in the specified file
     * @param filename the name of the file to read from
     * @return An ArrayList of intersections that were read from the file
     */
    public static ArrayList<Intersection> readIntersectionsFromFile(String filename) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Intersection.class, new IntersectionDeserializer())
                .create();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));

            Intersection[] intersectionsFromFile = gson.fromJson(bufferedReader, Intersection[].class);

            ArrayList<Intersection> intersections = new ArrayList<>(Arrays.asList(intersectionsFromFile));

            return intersections;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Read the relationships stored in the specified file.
     *
     * The hashmap values
     * - Key - either sourceIntersection or destinationIntersection
     * - Value - the id of the intersection the relationship is with
     *
     * @param filename the name of the file to read from
     * @return An array of hashmaps that represent the relationships that were read from the file
     */
    public static HashMap<String, Integer>[] readRelationshipsFromFile(String filename) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(HashMap.class, new RelationshipDeserializer())
                .create();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));

            HashMap<String, Integer>[] relationships = gson.fromJson(bufferedReader, HashMap[].class);

            return relationships;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}