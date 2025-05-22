import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idatt2003.boardgame.Model.Card;
import edu.ntnu.idatt2003.boardgame.Model.CardManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CardManagerTest {
    CardManager cardManager;
    @BeforeEach
    public void setUp() {
        cardManager = new CardManager("src/main/resources/cards.json");
        cardManager.loadCardsFromFile();
    }

    //-------------------------Positive Tests----------------------------
    @Test
    public void testIfCardManagerHoldsCards() {
        List<?> cards = cardManager.getCards();
        for (Object card : cards) {
            assertInstanceOf(Card.class, card);
        }
        assertFalse(cardManager.getCards().isEmpty());
    }

    @Test
    public void testSendAlertMessage() {
        cardManager.drawCard();
        String cardText = cardManager.getLastDrawnCard().text;

        assertEquals(cardText, cardManager.sendAlert());
    }

}
