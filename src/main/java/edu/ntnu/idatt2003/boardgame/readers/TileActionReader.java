package edu.ntnu.idatt2003.boardgame.readers;

import com.google.gson.*;

import edu.ntnu.idatt2003.boardgame.Model.actions.*;

import java.lang.reflect.Type;

/**
 * Custom JSON deserializer for {@link TileAction} objects.
 * <p>
 * This class implements {@link JsonDeserializer} and is used by Gson to convert JSON representations
 * of tile actions into their corresponding Java objects. It inspects the {@code type} field in the JSON
 * and returns the appropriate subclass of {@code TileAction}.
 *
 * <p>Supported types include:
 * <ul>
 *   <li>{@code ladder} - returns a {@link LadderAction}</li>
 *   <li>{@code snake} - returns a {@link SnakeAction}</li>
 *   <li>{@code portal} - returns a {@link PortalAction}</li>
 *   <li>{@code win} - returns a {@link WinAction}</li>
 *   <li>{@code jail} - returns a {@link JailAction}</li>
 *   <li>{@code draw} - returns a {@link DrawCardAction}</li>
 * </ul>
 *
 * <p>If an unknown type is encountered, a {@link JsonParseException} is thrown.
 */
public class TileActionReader implements JsonDeserializer<TileAction> {

    /**
     * Deserializes a JSON element into a specific {@link TileAction} subclass based on the {@code type} field.
     *
     * @param json the JSON element to deserialize
     * @param typeOfT the type of the object to deserialize to
     * @param context the deserialization context
     * @return a specific {@code TileAction} instance
     * @throws JsonParseException if the type is unknown or if the JSON structure is invalid
     */
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
            case "win" -> new WinAction(destination,type);
            case "jail" -> new JailAction(destination,type);
            case "draw" -> new DrawCardAction(destination, type);
            default -> throw new JsonParseException("Unknown action type: " + type);
        };
    }
}

