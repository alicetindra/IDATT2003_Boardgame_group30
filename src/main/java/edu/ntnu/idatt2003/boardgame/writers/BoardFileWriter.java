package edu.ntnu.idatt2003.boardgame.writers;

import com.google.gson.JsonObject;

public interface BoardFileWriter {
  void writeJsonToFile(JsonObject jsonObject, String filePath);
}