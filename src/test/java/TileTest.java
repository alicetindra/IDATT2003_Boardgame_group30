import static org.junit.jupiter.api.Assertions.*;
import edu.ntnu.idatt2003.boardgame.Model.*;
import edu.ntnu.idatt2003.boardgame.Model.actions.*;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TileTest {
    Tile tile;
    VBox tileBox;

    @BeforeEach
    public void setUp() {
        tile = new Tile(3);
        tileBox = new VBox();
        tile.setTileBox(tileBox);
        tile.setAction(new SnakeAction(0,"snake"));
        tile.setNext(4);
    }

    //------Positive tests------//
    @Test
    public void testGetId(){
        assertEquals(3,tile.getId());
    }
    @Test
    public void testGetTileBox(){
        assertEquals(tileBox,tile.getTileBox());
    }
    @Test
    public void testGetAction(){
        assertInstanceOf(SnakeAction.class,tile.getAction());
    }
    @Test
    public void testGetNextTileId(){
        assertEquals(4,tile.getNext());
    }

    //-------Negative--------//
    @Test
    public void testSetNullAction(){
        assertThrows(NullPointerException.class,()->tile.setAction(null));
    }
    @Test
    public void testMakeNegativeTileId(){
        assertThrows(IllegalArgumentException.class,()->new Tile(-5));
    }

}
