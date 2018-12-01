package hci.phasedifference.recollect.datamodel;

import android.os.Build;
import androidx.annotation.RequiresApi;
import hci.phasedifference.recollect.datamodel.datarepresentaion.Card;
import hci.phasedifference.recollect.datamodel.datarepresentaion.CardSetImpl;
import hci.phasedifference.recollect.datamodel.datarepresentaion.LeitnerLevels;

import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.stream.Collectors;

public class ActiveDataHandler {

    private static ActiveDataHandler instance;

    CardSetImpl curCardSet;
    Stack<Card> displayCardStack;


    private CardViewModel viewModel;

    public void setViewModel(CardViewModel viewModel) {
        this.viewModel = viewModel;
    }

    private ActiveDataHandler() {
    }

    public static ActiveDataHandler getInstance() {
        if (instance == null) {
            instance = new ActiveDataHandler();
        }
        return instance;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void activateCardSet(CardSetImpl cardSet) {
        curCardSet = cardSet;
        displayCardStack = new Stack<>();

        List<Card> list = curCardSet.getCards();
        List<Card> displayList = list
                .stream()
                .filter(b -> (b.getLevel() != LeitnerLevels.MASTERED))
                .collect(Collectors.toList());

        for (Card c : displayList) {
            displayCardStack.push(c);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Card> getMasteredList() {

        List<Card> displayList = curCardSet.getCards()
                .stream()
                .filter(b -> (b.getLevel() == LeitnerLevels.MASTERED))
                .collect(Collectors.toList());
        return displayList;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Card> getStarredList() {

        List<Card> displayList = curCardSet.getCards()
                .stream()
                .filter(b -> (b.isStarred()))
                .collect(Collectors.toList());
        return displayList;
    }

    public Stack<Card> getDisplayStack() {
        return displayCardStack;
    }

    public Stack<Card> setUserGuess(Card c, boolean guess) {
        Card newCard = curCardSet.setUserGuess(c, guess);
        if (newCard.getLevel() != LeitnerLevels.MASTERED) {
            int index = 0;
            Random r = new Random();
            if (displayCardStack.size() != 0) {
                index = Math.abs(r.nextInt()) % displayCardStack.size();
            }
            displayCardStack.insertElementAt(newCard, index);
        }
        return displayCardStack;

    }

    public void addCardSet(CardSetImpl c) {
        viewModel.addAcardSet(c);
    }


}
