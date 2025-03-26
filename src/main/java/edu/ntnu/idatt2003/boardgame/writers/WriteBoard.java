package edu.ntnu.idatt2003.boardgame.writers;

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
        if (n == 90) {
            JsonArray ladderJsonArray = new JsonArray();

            for (int i = 1; i <= n; i++) {
                //Tile map
                JsonObject tileJson = new JsonObject();
                tileJson.addProperty("id", i);

                //adding the next tile
                if (i < n) {
                    tileJson.addProperty("next", i + 1);
                }

                //action map
                //Ladders
                if (i == 5) {
                    JsonObject actionObj = new JsonObject();
                    actionObj.addProperty("type", "ladder");
                    actionObj.addProperty("destination", 25);
                    tileJson.add("action", actionObj);
                }
                if (i == 18) {
                    JsonObject actionObj = new JsonObject();
                    actionObj.addProperty("type", "ladder");
                    actionObj.addProperty("destination", 21);
                    tileJson.add("action", actionObj);
                }
                if (i == 55) {
                    JsonObject actionObj = new JsonObject();
                    actionObj.addProperty("type", "ladder");
                    actionObj.addProperty("destination", 73);
                    tileJson.add("action", actionObj);
                }
                //Snakes
                if (i == 28) {
                    JsonObject actionObj = new JsonObject();
                    actionObj.addProperty("type", "snake");
                    actionObj.addProperty("destination", 8);
                    tileJson.add("action", actionObj);
                }
                if (i == 42) {
                    JsonObject actionObj = new JsonObject();
                    actionObj.addProperty("type", "snake");
                    actionObj.addProperty("destination", 24);
                    tileJson.add("action", actionObj);
                }
                if (i == 70) {
                    JsonObject actionObj = new JsonObject();
                    actionObj.addProperty("type", "snake");
                    actionObj.addProperty("destination", 47);
                    tileJson.add("action", actionObj);
                }
                if (i == 87) {
                    JsonObject actionObj = new JsonObject();
                    actionObj.addProperty("type", "snake");
                    actionObj.addProperty("destination", 75);
                    tileJson.add("action", actionObj);
                }

                if (i == 49 || i == 63) {
                    JsonObject actionObj = new JsonObject();
                    actionObj.addProperty("type", "portal");
                    actionObj.addProperty("destination", 0);
                    tileJson.add("action", actionObj);
                }

                ladderJsonArray.add(tileJson);
            }
            tileBook.add("tiles", ladderJsonArray);
        }

        else if (n == 50) {

                JsonArray ladderJsonArray = new JsonArray();

                for (int i = 1; i <= n; i++) {
                    //Tile map
                    JsonObject tileJson = new JsonObject();
                    tileJson.addProperty("id", i);

                    //adding the next tile
                    if (i < n) {
                        tileJson.addProperty("next", i + 1);
                    }

                    //action map
                    //Ladders
                    if (i == 14) {
                        JsonObject actionObj = new JsonObject();
                        actionObj.addProperty("type", "ladder");
                        actionObj.addProperty("destination", 28);
                        tileJson.add("action", actionObj);
                    }
                    if (i == 37) {
                        JsonObject actionObj = new JsonObject();
                        actionObj.addProperty("type", "ladder");
                        actionObj.addProperty("destination", 42);
                        tileJson.add("action", actionObj);

                    }
                    //Snakes
                    if (i == 22) {
                        JsonObject actionObj = new JsonObject();
                        actionObj.addProperty("type", "snake");
                        actionObj.addProperty("destination", 2);
                        tileJson.add("action", actionObj);
                    }

                    if (i == 49) {
                        JsonObject actionObj = new JsonObject();
                        actionObj.addProperty("type", "snake");
                        actionObj.addProperty("destination", 30);
                        tileJson.add("action", actionObj);
                    }
                    //Portals
                    if (i == 46) {
                        JsonObject actionObj = new JsonObject();
                        actionObj.addProperty("type", "portal");
                        actionObj.addProperty("destination", 0);
                        tileJson.add("action", actionObj);
                    }

                    ladderJsonArray.add(tileJson);
                }
                 tileBook.add("tiles", ladderJsonArray);


        }

        else if (n == 110) {

                JsonArray ladderJsonArray = new JsonArray();

                for (int i = 1; i <= n; i++) {
                    //Tile map
                    JsonObject tileJson = new JsonObject();
                    tileJson.addProperty("id", i);

                    //adding the next tile
                    if (i < n) {
                        tileJson.addProperty("next", i + 1);
                    }

                    //action map
                    //Ladders
                    if (i == 8) {
                        JsonObject actionObj = new JsonObject();
                        actionObj.addProperty("type", "ladder");
                        actionObj.addProperty("destination", 27);
                        tileJson.add("action", actionObj);
                    }
                    if (i == 38) {
                        JsonObject actionObj = new JsonObject();
                        actionObj.addProperty("type", "ladder");
                        actionObj.addProperty("destination", 63);
                        tileJson.add("action", actionObj);

                    }
                    if (i == 48) {
                        JsonObject actionObj = new JsonObject();
                        actionObj.addProperty("type", "ladder");
                        actionObj.addProperty("destination", 93);
                        tileJson.add("action", actionObj);

                    }
                    if (i == 82) {
                        JsonObject actionObj = new JsonObject();
                        actionObj.addProperty("type", "ladder");
                        actionObj.addProperty("destination", 101);
                        tileJson.add("action", actionObj);

                    }
                    //Snakes
                    if (i == 24) {
                        JsonObject actionObj = new JsonObject();
                        actionObj.addProperty("type", "snake");
                        actionObj.addProperty("destination", 5);
                        tileJson.add("action", actionObj);
                    }

                    if (i == 52) {
                        JsonObject actionObj = new JsonObject();
                        actionObj.addProperty("type", "snake");
                        actionObj.addProperty("destination", 20);
                        tileJson.add("action", actionObj);
                    }
                    if (i == 62) {
                        JsonObject actionObj = new JsonObject();
                        actionObj.addProperty("type", "snake");
                        actionObj.addProperty("destination", 41);
                        tileJson.add("action", actionObj);
                    }
                    if (i == 98) {
                        JsonObject actionObj = new JsonObject();
                        actionObj.addProperty("type", "snake");
                        actionObj.addProperty("destination", 76);
                        tileJson.add("action", actionObj);
                    }
                    //Portals
                    if (i == 19) {
                        JsonObject actionObj = new JsonObject();
                        actionObj.addProperty("type", "portal");
                        actionObj.addProperty("destination", 0);
                        tileJson.add("action", actionObj);
                    }
                    if (i == 45) {
                        JsonObject actionObj = new JsonObject();
                        actionObj.addProperty("type", "portal");
                        actionObj.addProperty("destination", 0);
                        tileJson.add("action", actionObj);
                    }
                    if (i == 74) {
                        JsonObject actionObj = new JsonObject();
                        actionObj.addProperty("type", "portal");
                        actionObj.addProperty("destination", 0);
                        tileJson.add("action", actionObj);
                    }

                    ladderJsonArray.add(tileJson);
                }
                 tileBook.add("tiles", ladderJsonArray);

        }

        return tileBook;
    }
}
