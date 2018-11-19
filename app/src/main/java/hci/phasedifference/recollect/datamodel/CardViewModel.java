package hci.phasedifference.recollect.datamodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CardViewModel extends AndroidViewModel {

    private CardRepository repo;
    private LiveData<List<AvailableCardSets>> allCards;

    public CardViewModel(@NonNull Application application) {
        super(application);
        repo = new CardRepository(application);
        allCards = repo.getAllCards();
    }

    public void insert(AvailableCardSets availableCardSets) {
        repo.insert(availableCardSets);
    }

    public void delete(AvailableCardSets availableCardSets) {
        repo.delete(availableCardSets);
    }

    public void update(AvailableCardSets availableCardSets) {
        repo.update(availableCardSets);
    }

    public void deleteAll() {
        repo.deleteAll();
    }

    public LiveData<List<AvailableCardSets>> getAllCards() {
        return allCards;
    }
}
