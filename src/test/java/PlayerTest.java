import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idatt2003.boardgame.Model.BoardGame;
import edu.ntnu.idatt2003.boardgame.Model.Player;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class PlayerTest {
    Player player;
    @BeforeEach
    public void setUp() {
        player = new Player("Rachel","green");
    }

    //------------------------Positive tests--------------------------------
    @Test
    public void testGetName() {
        assertEquals("Rachel",player.getName());
    }
    @Test
    public void testGetColor() {
        assertEquals("green",player.getColor());
    }
    @Test
    public void testEditAndGetMoney() {
        player.editMoney(10);
        assertEquals(10,player.getMoney());
    }

    @Test
    public void testSetAndGetImageView() {
        ImageView dummyView = new ImageView();
        player.setImageView(dummyView);
        assertInstanceOf(ImageView.class, player.getImageView());
        assertSame(dummyView, player.getImageView());
    }

    @Test
    public void testSetAndGetBoardGame(){
        BoardGame boardGame = new BoardGame();
        player.setBoardGame(boardGame);
        assertEquals(player.getBoardGame(),boardGame);
    }

    @Test
    public void testSetInAndOutOfJail(){
        player.setInJail(true);
        assertTrue(player.isInJail());

        player.setInJail(false);
        assertFalse(player.isInJail());
    }


    //--------------------------Negative tests-------------------------

    @Test
    public void testMakePlayerEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> {new Player(" ","red");});
    }
    @Test
    public void testMakePlayerEmptyColor() {
        assertThrows(IllegalArgumentException.class, () -> {new Player("Maria","");});
    }
    @Test
    public void testMakePlayerNullName() {
        assertThrows(NullPointerException.class, () -> {new Player(null,"red");});
    }
    @Test
    public void testMakePlayerNullColor() {
        assertThrows(NullPointerException.class, () -> {new Player("Maria",null);});
    }

}
