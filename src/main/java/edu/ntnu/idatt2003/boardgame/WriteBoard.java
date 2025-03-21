package edu.ntnu.idatt2003.boardgame;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;

public class WriteBoard {
    public void writeJsonToFile(JsonObject jsonObject, String filePath) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(jsonObject.toString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JsonObject serializeTiles(int n) {
        JsonObject tileBook = new JsonObject();

        JsonArray ladderJsonArray = new JsonArray();

        for (int i = 0; i<=n; i++) {
            //Tile map
            JsonObject tileJson = new JsonObject();
            tileJson.addProperty("id", i);

            //adding the next tile
            if(i<n){
                tileJson.addProperty("next", i+1);
            }
            else{
                tileJson.addProperty("next", 0);
            }

            //action map
            if(i == 5){
                JsonObject actionObj = new JsonObject();
                actionObj.addProperty("type", "ladder");
                actionObj.addProperty("destination", 12);
                tileJson.add("action", actionObj);
            }
            if(i == 14){
                JsonObject actionObj = new JsonObject();
                actionObj.addProperty("type", "snake");
                actionObj.addProperty("destination", 3);
                tileJson.add("action", actionObj);
            }
            if(i == 6){
                JsonObject actionObj = new JsonObject();
                actionObj.addProperty("type", "portal");
                actionObj.addProperty("destination", 0);
                tileJson.add("action", actionObj);
            }

            ladderJsonArray.add(tileJson);
        }
        /*

        JsonArray diamondJsonArray = new JsonArray();
        for (int i = 0; i<=n; i++) {
            JsonObject tileJson = new JsonObject();
            tileJson.addProperty("id", i);
            if(i == 1){
                tileJson.addProperty("diamond", 12);
            }
            if(i == 2){
                tileJson.addProperty("diamond", 37);
            }

            diamondJsonArray.add(tileJson);
        }
        */


        tileBook.add("tiles", ladderJsonArray);
        //tileBook.add("tileDiamond", diamondJsonArray);

        return tileBook;
    }
}
