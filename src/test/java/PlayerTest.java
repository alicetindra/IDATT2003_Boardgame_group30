import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idatt2003.boardgame.BoardGame;
import edu.ntnu.idatt2003.boardgame.Player;
import edu.ntnu.idatt2003.boardgame.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class PlayerTest {

  private Player player;
  private BoardGame boardGame;

  private Tile tile = new Tile(1);
  private Tile tile2 = new Tile(2);
  private Tile tile3 = new Tile(3);

  @BeforeEach
  public void setUp() {
    BoardGame game = new BoardGame();
    player = new Player("Tindra", game);

    game.createBoard();
    game.getBoard().addTile(tile);
    game.getBoard().addTile(tile2);
    game.getBoard().addTile(tile3);
  }

  //--------------------------Positive tests----------------------------------

  @Test
  public void placeOnTileValidInput(){
    player.placeOnTile(tile2);
    assertEquals(tile2, player.getCurrentTile());

  }



}
