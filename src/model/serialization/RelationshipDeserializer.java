package model.serialization;

import com.google.gson.*;
import model.board.Coordinate;
import model.board.Intersection;

import java.lang.reflect.Type;
import java.util.HashMap;

public class RelationshipDeserializer implements JsonDeserializer<HashMap<String, Integer>> {
    @Override
    public HashMap<String, Integer> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        int sourceIntersection = jsonObject.get("sourceIntersection").getAsInt();
        int destinationIntersection = jsonObject.get("destinationIntersection").getAsInt();

        HashMap<String, Integer> relationship = new HashMap<>();

        relationship.put("sourceIntersection", sourceIntersection);
        relationship.put("destinationIntersection", destinationIntersection);

        return relationship;
    }
}
