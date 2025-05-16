import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idatt2003.boardgame.Model.Tile;
import edu.ntnu.idatt2003.boardgame.Model.actions.SnakeAction;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TileTest {
    Tile tile;
    VBox vbox = new VBox();

    @BeforeEach
    public void setUp() {
        tile = new Tile(2);
        tile.setAction(new SnakeAction(1,"Snake"));
        tile.setTileBox(vbox);
    }

    //-------------------------Positive Tests----------------------------
    @Test
    public void getIdTest() {
        assertEquals(2, tile.getId());
    }

    @Test
    public void getActionTest() {
        assertInstanceOf(SnakeAction.class, tile.getAction());
    }

    @Test
    public void getTileBoxTest() {
        assertEquals(vbox, tile.getTileBox());
    }
}
