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

    public static void writeIntersectionsToFile(String filename, List<Intersection> intersections) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Intersection.class, new IntersectionSerializer())
                .setPrettyPrinting()
                .create();

        try {
            FileWriter writer = new FileWriter(filename);
            writer.write(gson.toJson(intersections));
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeRelationshipsToFile(String filename, List<Intersection> intersections) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Intersection.class, new RelationshipsSerializer())
                .setPrettyPrinting()
                .create();

        try {
            FileWriter writer = new FileWriter(filename);
            writer.write(gson.toJson(intersections));
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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