package hci.phasedifference.recollect.viewpackage.screens;


import androidx.recyclerview.widget.DiffUtil;
import hci.phasedifference.recollect.datamodel.datarepresentaion.Card;

import java.util.List;

public class CardDiffCallback extends DiffUtil.Callback {

    private final List<Card> oldList;
    private final List<Card> newList;

    public CardDiffCallback(List<Card> oldList, List<Card> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldPosition, int newPosition) {
        return oldList.get(oldPosition).equals(newPosition);
    }

    @Override
    public boolean areContentsTheSame(int oldPosition, int newPosition) {
        Card oldCard = oldList.get(oldPosition);
        Card newCard = newList.get(newPosition);
        return oldCard.equals(newCard);
    }

}
