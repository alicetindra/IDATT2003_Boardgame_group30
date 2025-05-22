import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idatt2003.boardgame.Model.Player;
import edu.ntnu.idatt2003.boardgame.Model.PlayerHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class PlayerHolderTest {

   private PlayerHolder playerHolder;
   private Player player1;
   private Player player2;
   private List<Player> players;

    @BeforeEach
    public void setUp() {
        playerHolder = new PlayerHolder();
        player1 = new Player("Lukas","red");
        player2 = new Player("Mari","yellow");
        players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
    }

    //------------------------Positive tests--------------------------------
    @Test
    public void testSetPlayerList() {
        playerHolder.setPlayers(players);
        assertEquals(players, playerHolder.getPlayers());
    }
    @Test
    public void testSetCurrentPlayer() {
        playerHolder.setCurrentPlayer(player1);
        assertEquals(player1, playerHolder.getCurrentPlayer());
        playerHolder.setCurrentPlayer(player2);
        assertEquals(player2, playerHolder.getCurrentPlayer());
    }
    @Test
    public void testGetNextPlayerIndex() {
        playerHolder.setPlayers(players);
        playerHolder.setCurrentPlayer(player1);
        assertEquals(1, playerHolder.getNextPlayerIndex());

        playerHolder.setCurrentPlayer(player2);
        assertEquals(0, playerHolder.getNextPlayerIndex());
    }
    @Test
    public void testGetPlayersNameWithIndex() {
        playerHolder.setPlayers(players);
        assertEquals(playerHolder.getPlayerNameWithIndex(1), player2.getName());
    }

}
