package model.serialization;

import com.google.gson.*;
import model.board.Intersection;
import model.board.Path;

import java.lang.reflect.Type;

public class RelationshipsSerializer implements JsonSerializer<Intersection> {
    @Override
    public JsonElement serialize(Intersection intersection, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray pathsArray = new JsonArray();

        for (Path path : intersection.getPaths()) {
            // we need to create a new object since an intersection with multiple paths will override the previous property.
            // this was painful to debug and find :/
            JsonObject pathObject = new JsonObject();
            pathObject.addProperty("sourceIntersection", path.getSourceIntersection().getId());
            pathObject.addProperty("destinationIntersection", path.getDestinationIntersection().getId());
            pathsArray.add(pathObject);
        }

        return pathsArray;
    }
}