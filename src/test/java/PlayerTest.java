import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idatt2003.boardgame.BoardGame;
import edu.ntnu.idatt2003.boardgame.Player;
import edu.ntnu.idatt2003.boardgame.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class PlayerTest {

  private Player player;

  @BeforeEach
  public void setUp() {
    BoardGame game = new BoardGame();
    player = new Player("TIndra", game);
  }

  //--------------------------Positive tests----------------------------------

  @Test
  public void placeOnTileValidInput(){
    Tile tile = new Tile(5);
    player.placeOnTile(tile);


  }



}
