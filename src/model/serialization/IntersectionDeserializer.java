package model.serialization;

import com.google.gson.*;
import model.board.Coordinate;
import model.board.Intersection;

import java.lang.reflect.Type;

public class IntersectionDeserializer implements JsonDeserializer<Intersection> {
    @Override
    public Intersection deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        int id = jsonObject.get("id").getAsInt();

        JsonObject coordinateObject = jsonObject.get("coordinate").getAsJsonObject();

        int xCoordinate = coordinateObject.get("x").getAsInt();
        int yCoordinate = coordinateObject.get("y").getAsInt();

        return new Intersection(id, new Coordinate(xCoordinate, yCoordinate));
    }
}
