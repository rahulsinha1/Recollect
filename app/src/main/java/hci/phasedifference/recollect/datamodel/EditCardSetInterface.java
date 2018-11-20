package hci.phasedifference.recollect.datamodel;

public interface EditCardSetInterface {

    void addCard(SingleCard card);

    void removeCard(SingleCard card);

    void setStarred(SingleCard card, boolean starred);

}