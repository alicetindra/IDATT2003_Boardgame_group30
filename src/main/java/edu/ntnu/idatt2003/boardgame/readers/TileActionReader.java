package edu.ntnu.idatt2003.boardgame.readers;

import com.google.gson.*;

import edu.ntnu.idatt2003.boardgame.Model.Board;
import edu.ntnu.idatt2003.boardgame.Model.Tile;
import edu.ntnu.idatt2003.boardgame.Model.actions.LadderAction;
import edu.ntnu.idatt2003.boardgame.Model.actions.PortalAction;
import edu.ntnu.idatt2003.boardgame.Model.actions.SnakeAction;
import edu.ntnu.idatt2003.boardgame.Model.actions.TileAction;
import java.lang.reflect.Type;

public class TileActionReader implements JsonDeserializer<TileAction> {

    @Override
    public TileAction deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (!json.isJsonObject()) return null;

        JsonObject obj = json.getAsJsonObject();
        String type = obj.get("type").getAsString();
        int destination = obj.get("destination").getAsInt();

        return switch (type.toLowerCase()) {
            case "ladder" -> new LadderAction(destination,type);
            case "snake" -> new SnakeAction(destination,type);
            case "portal" -> new PortalAction();
            default -> throw new JsonParseException("Unknown action type: " + type);
        };
    }
}

