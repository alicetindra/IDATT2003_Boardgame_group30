import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idatt2003.boardgame.Model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;


public class PropertyHolderTest {

    private PropertyHolder propertyHolder;
    private Player player;

    @BeforeEach
    public void setUp() {
        propertyHolder = new PropertyHolder();
        player = new Player("Lars","pink");
        propertyHolder.setProperties(10);
    }

    //-------------------------Positive Tests----------------------------
    @Test
    public void testGetProperties() {
        HashMap<?,?> props = propertyHolder.getProperties();
        for (Object prop : props.keySet()) {
            assertInstanceOf(Integer.class, prop);
        }
        assertEquals(propertyHolder.getProperties().size(), 10);
    }

    @Test
    public void testSetOwner() {
        assertNull(propertyHolder.getProperties().get(0));
        propertyHolder.setOwner(1,player);
        assertEquals(propertyHolder.getProperties().get(1), player);
    }

    @Test
    public void testRemoveOwner() {
        propertyHolder.setOwner(1,player);
        propertyHolder.removeOwner(1);
        assertNull(propertyHolder.getProperties().get(0));
    }

    @Test
    public void testGetPropertyPrice() {
        assertEquals(140,propertyHolder.getPrice(1));
        assertEquals(200,propertyHolder.getPrice(3));
        assertEquals(300,propertyHolder.getPrice(7));
        assertEquals(360,propertyHolder.getPrice(10));

    }

    @Test
    public void testGetPropertyFee() {
        assertEquals(35,propertyHolder.getFee(1));
        assertEquals(50,propertyHolder.getFee(3));
        assertEquals(75,propertyHolder.getFee(7));
        assertEquals(90,propertyHolder.getFee(10));
    }


}
