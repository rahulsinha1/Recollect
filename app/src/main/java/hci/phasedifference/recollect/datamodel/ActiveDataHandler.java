package hci.phasedifference.recollect.datamodel;

import android.os.Build;
import androidx.annotation.RequiresApi;
import hci.phasedifference.recollect.datamodel.datarepresentaion.Card;
import hci.phasedifference.recollect.datamodel.datarepresentaion.CardSetImpl;
import hci.phasedifference.recollect.datamodel.datarepresentaion.LeitnerLevels;

import java.util.List;
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
        displayCardStack = new Stack<>();
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
        displayCardStack = getDisplayStack();
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
    public List<Card> getAllCardsList() {

        List<Card> displayList = curCardSet.getCards()
                .stream()
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


    @RequiresApi(api = Build.VERSION_CODES.N)
    public Stack<Card> getDisplayStack() {
        displayCardStack = new Stack<>();

        List<Card> list = curCardSet.getCards();
        List<Card> displayList = list
                .stream()
                .filter(b -> (b.getLevel() != LeitnerLevels.MASTERED))
                .collect(Collectors.toList());

        for (Card c : displayList) {
            displayCardStack.push(c);
        }
        return displayCardStack;
    }

    public Stack<Card> setUserGuess(Card c, boolean guess) {
        Card newCard = curCardSet.setUserGuess(c, guess);
        return displayCardStack;

    }

    public String getCurrentTitle() {
        return curCardSet.getTitle();
    }
    public void addCardSet(CardSetImpl c) {
        viewModel.addAcardSet(c);
    }

    public void relearnCardSet() {
        curCardSet.relearn();
    }

    public void removeCardSet(CardSetImpl c) {
        viewModel.removeCardSet(c);
    }

    public void removeCard(Card c) {
        curCardSet.removeCard(c);
    }


    public void addCard(String word, String defn) {
        curCardSet.addCard(word, defn);
    }

}
