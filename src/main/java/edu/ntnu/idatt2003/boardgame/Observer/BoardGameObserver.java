package edu.ntnu.idatt2003.boardgame.Observer;

import edu.ntnu.idatt2003.boardgame.Model.BoardGame;

/**
 * Interface for observing changes in a {@link BoardGame} instance.
 * <p>
 * Classes that implement this interface can be notified when significant events
 * occur in the game, such as player movement, player lands on actions, or winner is declared.
 * This follows the Observer design pattern, enabling a decoupled architecture
 * between the game logic and the UI or other components.
 */
public interface BoardGameObserver {

  /**
   * Called when an observed {@link BoardGame} triggers an event.
   *
   * @param event      a string describing the event that occurred (e.g., "playerMoved", "gameStarted")
   * @param boardGame  the game instance where the event took place
   */
  void update(String event, BoardGame boardGame);

}
