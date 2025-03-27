package edu.ntnu.idatt2003.boardgame.writers;

import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;

public class BoardFileWriterGson implements BoardFileWriter{

    @Override
    public void writeJsonToFile(JsonObject jsonObject, String filePath) {
            try (FileWriter fileWriter = new FileWriter(filePath)) {
                fileWriter.write(jsonObject.toString());
                fileWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
