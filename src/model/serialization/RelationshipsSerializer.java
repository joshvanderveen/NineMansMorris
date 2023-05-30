package model.serialization;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import model.board.Intersection;
import model.board.Path;

import java.lang.reflect.Type;

public class RelationshipsSerializer implements JsonSerializer<Intersection> {
    @Override
    public JsonElement serialize(Intersection intersection, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject mainObject = new JsonObject();

        for (Path path : intersection.getPaths()) {
            mainObject.addProperty("sourceIntersection", path.getSourceIntersection().getId());
            mainObject.addProperty("destinationIntersection", path.getDestinationIntersection().getId());
        }

        return mainObject;
    }
}
