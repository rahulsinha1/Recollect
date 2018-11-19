package hci.phasedifference.recollect.datamodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class CardViewModel extends AndroidViewModel {

    private CardRepository repo;
    private LiveData<List<Card>> allCards;

    public CardViewModel(@NonNull Application application) {
        super(application);
        repo = new CardRepository(application);
        allCards = repo.getAllCards();
    }

    public void insert(Card card) {
        repo.insert(card);
    }

    public void delete(Card card) {
        repo.delete(card);
    }

    public void update(Card card) {
        repo.update(card);
    }

    public void deleteAll() {
        repo.deleteAll();
    }

    public LiveData<List<Card>> getAllCards() {
        return allCards;
    }
}
