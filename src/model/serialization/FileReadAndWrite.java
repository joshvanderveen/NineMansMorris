package model.serialization;

import com.google.gson.*;
import model.board.Intersection;

import java.io.*;
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
            String result = gson.toJson(intersections);
            result = flattenJsonArray(result);
            System.out.println(result);
            writer.write(result);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String flattenJsonArray(String jsonArrayString) {
        // Parse the JSON string into a JsonArray
        JsonArray jsonArray = JsonParser.parseString(jsonArrayString).getAsJsonArray();

        // Create a new JsonArray to store the flattened objects
        JsonArray flattenedArray = new JsonArray();

        // Iterate over the elements in the input JsonArray
        for (JsonElement element : jsonArray) {
            // Check if the element is an array
            if (element.isJsonArray()) {
                // If it is an array, iterate over its elements and add them to the flattened array
                JsonArray subArray = element.getAsJsonArray();
                for (JsonElement subElement : subArray) {
                    flattenedArray.add(subElement);
                }
            } else {
                // If it is not an array, add the element directly to the flattened array
                flattenedArray.add(element);
            }
        }

        // Convert the flattened JsonArray back to a JSON string
        Gson gson = new Gson();
        return gson.toJson(flattenedArray);
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


    public static boolean deleteBoard(String directoryName) {
        File directory = new File(directoryName);

        if (directory.exists() && directory.isDirectory()) {
            try {
                File[] files = directory.listFiles();

                if (files != null) {
                    for (File file : files) {
                        if (file.isDirectory()) {
                            // Recursive call to delete subdirectories
                            deleteBoard(file.getAbsolutePath());
                        } else {
                            if (file.delete()) {
                                System.out.println("File deleted successfully: " + file.getAbsolutePath());
                            } else {
                                System.out.println("Failed to delete the file: " + file.getAbsolutePath());
                            }
                        }
                    }
                }

                if (directory.delete()) {
                    System.out.println("Directory deleted successfully: " + directory.getAbsolutePath());
                    return true;
                } else {
                    System.out.println("Failed to delete the directory: " + directory.getAbsolutePath());
                    return false;
                }
            } catch (SecurityException e) {
                System.out.println("Permission denied while deleting the directory: " + directory.getAbsolutePath());
                e.printStackTrace();
                return false;
            }
        } else {
            System.out.println("Directory does not exist: " + directory.getAbsolutePath());
            return false;
        }
    }


}