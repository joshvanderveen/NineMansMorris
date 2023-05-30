package model.serialization;

import com.google.gson.*;
import model.board.Intersection;
import model.board.Path;

import java.lang.reflect.Type;
import java.util.List;

public class IntersectionSerializer implements JsonSerializer<Intersection> {
    @Override
    public JsonElement serialize(Intersection intersection, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject mainObject = new JsonObject();
        mainObject.addProperty("id", intersection.getId());

        JsonObject coordinateObject = new JsonObject();

        coordinateObject.addProperty("x", intersection.getXCoordinate());
        coordinateObject.addProperty("y", intersection.getYCoordinate());

        mainObject.add("coordinate", coordinateObject);

        return mainObject;
    }
}
