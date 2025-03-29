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
      System.out.println("Board data successfully written to " + filePath);
    } catch (IOException e) {
      System.out.println("Error writing to file: " + filePath);
    }
  }
}