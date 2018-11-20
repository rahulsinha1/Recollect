package hci.phasedifference.recollect.datamodel.datarepresentaion;

import java.util.List;

public interface CardSetInterface {

    void addCard(String word, String definition);

    void removeCard(Card card);

    Card editCard(Card oldcard, String word, String definition);

    Card setUserGuess(Card card, boolean answer);

    Card setStarred(Card c, boolean starred);

    List<Card> getCards();

    String getCardsetTitle();
}