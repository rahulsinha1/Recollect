package hci.phasedifference.recollect.datamodel;

import hci.phasedifference.recollect.datamodel.datarepresentaion.Card;
import hci.phasedifference.recollect.datamodel.datarepresentaion.CardSetImpl;
import hci.phasedifference.recollect.datamodel.datarepresentaion.LeitnerLevels;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static junit.framework.TestCase.*;

public class CardSetImplTest {

    List<String> words;
    List<String> definitions;
    private final String card1 = "Word: Der Tisch defn: The Table";
    private CardSetImpl cardSet;
    private CardSetImpl emptyCardSet;
    private String card2 = "Word: Die Tasche defn: The Bag";
    private String card3 = "Word: Danke defn: Thank you";


    @org.junit.Before
    public void setUp() {
        cardSet = new CardSetImpl("German");
        emptyCardSet = new CardSetImpl("FrenchEmpty");

        words = new ArrayList<>();
        definitions = new ArrayList<>();
        words.add("Der Tisch");
        words.add("Die Tasche");
        words.add("Danke");

        definitions.add("The Table");
        definitions.add("The Bag");
        definitions.add("Thank you");

        cardSet.addCard(words.get(0), definitions.get(0));
        cardSet.addCard(words.get(1), definitions.get(1));
        cardSet.addCard(words.get(2), definitions.get(2));

    }

    @Test
    public void testEmptyCardSet() {
        assertEquals(0, emptyCardSet.getCards().size());
        assertEquals("FrenchEmpty", emptyCardSet.getCardsetTitle());

        emptyCardSet.addCard("w", "d");
        List<Card> list = emptyCardSet.getCards();
        assertEquals("w", list.get(0).getWord());
        assertEquals("d", list.get(0).getDefinition());

    }

    private boolean checkWordsAndDefinitions(List<Card> list) {
        int i = 0;
        for (Card c : list) {
            assertEquals(words.get(i), c.getWord());
            assertEquals(definitions.get(i), c.getDefinition());
            i++;
        }
        return true;
    }

    @Test
    public void testEditCard() {
        List<Card> list = cardSet.getCards();
        int i = 0;
        checkWordsAndDefinitions(list);

        //same card after edition
        cardSet.editCard(list.get(1), words.get(0), definitions.get(0));
        //should pass since the new list is not requested yet
        checkWordsAndDefinitions(list);

        //new list will contain new values
        list = cardSet.getCards();
        assertEquals(words.get(0), list.get(1).getWord());
        assertEquals(definitions.get(0), list.get(1).getDefinition());
    }

    @Test
    public void testSetStarred() {
        List<Card> list = cardSet.getCards();
        Card c = list.get(1);
        assertEquals(false, c.isStarred());

        c = cardSet.setStarred(c, true);
        assertEquals(true, c.isStarred());

        c = cardSet.setStarred(c, false);
        assertEquals(false, c.isStarred());

    }

    @Test
    public void testLevels() {
        List<Card> list = cardSet.getCards();
        Card c = list.get(0);
        assertEquals(LeitnerLevels.MASTERED, cardSet.setUserGuess(c, true).getLevel());
        c = list.get(1);
        assertEquals(LeitnerLevels.REVIEWING1, cardSet.setUserGuess(c, false).getLevel());
        assertEquals(LeitnerLevels.REVIEWING1, cardSet.setUserGuess(c, false).getLevel());
        c = cardSet.setUserGuess(c, false);
        c = cardSet.setUserGuess(c, true);
        assertEquals(LeitnerLevels.REVIEWING2, c.getLevel());
        c = cardSet.setUserGuess(c, false);
        assertEquals(LeitnerLevels.REVIEWING1, c.getLevel());
        c = cardSet.setUserGuess(c, true);
        assertEquals(LeitnerLevels.REVIEWING2, c.getLevel());
        c = cardSet.setUserGuess(c, true);
        assertEquals(LeitnerLevels.MASTERED, c.getLevel());
    }

    @Test
    public void testActiveDataHandlerGuessCorrect() {
        ActiveDataHandler adh = ActiveDataHandler.getInstance();
        adh.activateCardSet(cardSet);
        Stack<Card> displayStack = adh.getDisplayStack();

        Card card = displayStack.pop();

        displayStack = adh.setUserGuess(card, true);

        assertEquals(2, displayStack.size());
        card = displayStack.get(0);
        assertEquals(card.getWord(), words.get(0));
        assertEquals(card.getDefinition(), definitions.get(0));

        card = displayStack.get(1);
        assertEquals(card.getWord(), words.get(1));
        assertEquals(card.getDefinition(), definitions.get(1));

    }

    @Test
    public void testActiveDataHandlerGuessWrong() {

        //card should remain in the set put in a random location
        ActiveDataHandler adh = ActiveDataHandler.getInstance();
        adh.activateCardSet(cardSet);
        Stack<Card> displayStack = adh.getDisplayStack();

        Card card = displayStack.pop();

        displayStack = adh.setUserGuess(card, false);
        String stackstr = displayStack.toString();

        System.out.println(stackstr);

        assertEquals(3, displayStack.size());
        assertTrue(stackstr.contains(card1));
        assertTrue(stackstr.contains(card2));
        assertTrue(stackstr.contains(card3));

    }

    @Test
    public void testActiveDataHandlerGuessWrongLastCard() {

        //card should remain in the set put in a random location
        ActiveDataHandler adh = ActiveDataHandler.getInstance();
        adh.activateCardSet(cardSet);
        Stack<Card> displayStack = adh.getDisplayStack();

        displayStack = adh.setUserGuess(displayStack.pop(), true);
        displayStack = adh.setUserGuess(displayStack.pop(), true);

        String stackstr = displayStack.toString();

        assertEquals(1, displayStack.size());
        assertTrue(stackstr.contains(card1));
        assertFalse(stackstr.contains(card2));
        assertFalse(stackstr.contains(card3));

        displayStack = adh.setUserGuess(displayStack.pop(), false);
        assertTrue(displayStack.toString().contains(card1));

        displayStack = adh.setUserGuess(displayStack.pop(), false);
        assertTrue(displayStack.toString().contains(card1));
        assertEquals(LeitnerLevels.REVIEWING1, displayStack.get(0).getLevel());


        displayStack = adh.setUserGuess(displayStack.pop(), true);
        assertTrue(displayStack.toString().contains(card1));
        assertEquals(LeitnerLevels.REVIEWING2, displayStack.get(0).getLevel());

        Card card = displayStack.pop();
        displayStack = adh.setUserGuess(card, true);
        displayStack = adh.setUserGuess(card, true);

        assertEquals(0, displayStack.size());
        assertFalse(displayStack.toString().contains(card1));

        //get display stack should have zero elements
        displayStack = adh.getDisplayStack();
        assertEquals(0, displayStack.size());
    }

}