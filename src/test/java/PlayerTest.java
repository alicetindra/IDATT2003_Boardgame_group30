import static org.junit.jupiter.api.Assertions.*;
import edu.ntnu.idatt2003.boardgame.Model.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerTest {
    Player player;
    Board board;
    BoardGame boardGame;

    @BeforeEach
    public void setUp() {
        boardGame = new BoardGame();
        boardGame.initializeBoard("snakes and ladders", 50, "hardcodedBoardsTest.json");
        board = boardGame.getBoard();
        player = new Player("Linda","Blue");
        player.setBoardGame(boardGame);
        player.placeOnTile(board,4);
    }

    //------Positive tests------//
    @Test
    public void testGetName(){
        assertEquals("Linda",player.getName());
    }
    @Test
    public void testGetColor(){
        assertEquals("Blue",player.getColor());
    }
    @Test
    public void testGetBoard(){
        assertEquals(boardGame,player.getBoardGame());
    }
    @Test
    public void testPlaceOnTile(){
        player.placeOnTile(board,4);
        assertEquals(board.getTiles().get(3),player.getCurrentTile());
    }
    @Test
    public void testMove(){
        //In the hardcoded board, tile 10 has a ladder to tile 28.
        player.move(10);
        assertEquals(board.getTiles().get(27),player.getCurrentTile());
    }
    @Test
    public void testMovePastFinish(){
        player.placeOnTile(board,48);
        player.move(10);
        assertEquals(board.getTiles().get(41),player.getCurrentTile());
    }
    @Test
    public void testMovePastFinishAndLandOnAction(){
        //Tile 49 has a snake action, landing the player on tile 30
        player.placeOnTile(board,48);
        player.move(3);
        assertEquals(board.getTiles().get(29),player.getCurrentTile());
    }
    @Test
    public void testSetImageView(){
        ImageView imageView = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("Blue.png")));
        player.setImageView(imageView);
        assertEquals(imageView,player.getImageView());
    }

    //-------Negative tests-------//
    @Test
    public void testNullName(){
        assertThrows(IllegalArgumentException.class,()->{new Player(null,"Yellow");});
    }
    @Test
    public void testEmptyName(){
        assertThrows(IllegalArgumentException.class,()->{new Player(" ","Yellow");});
    }
    @Test
    public void testNullColor(){
        assertThrows(IllegalArgumentException.class,()->{new Player("Lisa",null);});
    }
    @Test
    public void testEmptyColor(){
        assertThrows(IllegalArgumentException.class,()->{new Player("Lisa"," ");});
    }
    @Test
    public void testEmptyImageView(){
        assertThrows(IllegalArgumentException.class,()->{player.setImageView(new ImageView());});
    }
    @Test
    public void moveNegativeDirection(){
        assertThrows(IllegalArgumentException.class,()->{player.move(-10);});
    }


}
