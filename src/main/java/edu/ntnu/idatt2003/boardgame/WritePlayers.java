package edu.ntnu.idatt2003.boardgame;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;

public class WritePlayers {
    public void writeJsonToFile(JsonObject jsonObject, String filePath) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(jsonObject.toString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JsonObject serializePlayers() {
        JsonObject playerBook = new JsonObject();

        JsonArray playerJsonArray = new JsonArray();
        for (int i = 0; i<=4; i++) {
            JsonObject playerJson = new JsonObject();
            playerJson.addProperty("name", "player"+i);
            if(i<4){
                playerJson.addProperty("next", "player"+(i+1));
            }
            else {
                playerJson.addProperty("next", "player"+0);
            }

            playerJsonArray.add(playerJson);
        }


        playerBook.add("players", playerJsonArray);

        return playerBook;
    }
}
