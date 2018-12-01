package hci.phasedifference.recollect.datamodel.datarepresentaion;

import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CardSetImpl implements CardSetInterface {

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    private final String title;
    private List<Card> cards;

    public CardSetImpl(String title) {
        this.title = title;
        cards = new ArrayList<>();
    }

    public CardSetImpl(CardSetImpl other) {
        title = other.title;
        cards = getListCopy(other.cards);
    }

    public CardSetImpl() {
        title = "";
        cards = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    @Override
    public void addCard(String word, String definition) {
        addCardHelper(cards.size(), word, definition, LeitnerLevels.NEW_WORD, false);
    }

    @Override
    public void removeCard(Card card) {
        cards.remove(card);
    }

    @Override
    public Card editCard(Card oldcard, String word, String definition) {
        int index = 0;
        Card cardToAdd = new Card(word, definition, LeitnerLevels.NEW_WORD, false);
        return editCardHelper(oldcard, cardToAdd);
    }

    private Card editCardHelper(Card oldcard, Card newCard) {
        int index = 0;
        for (Card c : cards) {
            index++;
            if (c.equals(oldcard)) {
                break;
            }
        }
        if (!newCard.equals(oldcard)) {
            cards.add(index, newCard);
            removeCard(oldcard);
        }
        return newCard;
    }

    @Override
    public Card setUserGuess(Card c, boolean answer) {
        LeitnerLevels level = getLevel(c.getLevel(), answer);
        Card newCard = new Card(c).setLevel(level);

        if (cards.remove(c)) {
            cards.add(newCard);
        }
        return newCard;
    }

    @Override
    public Card setStarred(Card c, boolean starred) {
        Card newCard = new Card(c).setStarred(starred);
        if (cards.remove(c)) {
            cards.add(newCard);
        }
        return newCard;
    }

    @Override
    public List<Card> getCards() {
        return getListCopy(cards);
    }

    @Override
    public String getCardsetTitle() {
        return title;
    }

    private List<Card> getListCopy(List<Card> orig) {
        List listToReturn = new ArrayList<>();
        for (Card c : orig) {
            listToReturn.add(new Card(c));
        }
        return listToReturn;
    }

    private boolean addCardHelper(int index, String word, String definition,
                                  LeitnerLevels level, boolean starred) {
        Card cardToAdd = new Card(word, definition, level, starred);
        Set<Card> cardSet = new HashSet<>();
        cardSet.addAll(cards);
        if (cardSet.add(cardToAdd)) {
            cards.add(index, cardToAdd);
        } else {
            throw new IllegalArgumentException("Duplicate item found");
        }
        return true;
    }

    private LeitnerLevels getLevel(LeitnerLevels curLevel, boolean guess) {
        switch (curLevel) {
            case NEW_WORD:
                return ((guess) ? LeitnerLevels.MASTERED : LeitnerLevels.REVIEWING1);
            case REVIEWING1:
                return ((guess) ? LeitnerLevels.REVIEWING2 : LeitnerLevels.REVIEWING1);
            case REVIEWING2:
                return ((guess) ? LeitnerLevels.MASTERED : LeitnerLevels.REVIEWING1);
            case MASTERED:
                return LeitnerLevels.MASTERED;
        }
        return LeitnerLevels.NEW_WORD;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public String toString() {
        return cards.stream()
                .map(b -> b.toString())
                .reduce("", (a, b) -> a + b);
    }
}
