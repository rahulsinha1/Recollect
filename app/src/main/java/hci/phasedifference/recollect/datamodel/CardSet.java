package hci.phasedifference.recollect.datamodel;

import java.util.ArrayList;
import java.util.List;

public class CardSet {

    private List<SingleCard> cards;

    public CardSet(List<SingleCard> cards) {
        this.cards = cards;
    }

    public CardSet() {
        cards = new ArrayList<SingleCard>();
    }

    public List<SingleCard> getCards() {
        return cards;
    }

}
