package hci.phasedifference.recollect.datamodel;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CardRepository {

    private CardDAO cardDao;
    private LiveData<List<AvailableCardSets>> allCards;

    public CardRepository(Application app) {

        CardDataBase carddb = CardDataBase.getInstance(app.getApplicationContext());
        cardDao = carddb.cardDao();
        allCards = cardDao.getAllCards();

    }


    void insert(AvailableCardSets availableCardSets) {
        new InsertCardAsyncTask(cardDao).execute(availableCardSets);

    }

    void update(AvailableCardSets availableCardSets) {
        new UpdateCardAsyncTask(cardDao).execute(availableCardSets);

    }

    void delete(AvailableCardSets availableCardSets) {
        new DeleteCardAsyncTask(cardDao).execute(availableCardSets);

    }

    void deleteAll() {
        new DeleteAllAsyncTask(cardDao).execute();
    }

    LiveData<List<AvailableCardSets>> getAllCards() {
        return allCards;
    }


    private static class InsertCardAsyncTask extends AsyncTask<AvailableCardSets, Void, Void> {

        private CardDAO cardDao;

        private InsertCardAsyncTask(CardDAO dao) {
            cardDao = dao;
        }

        @Override
        protected Void doInBackground(AvailableCardSets... availableCardSets) {
            cardDao.insert(availableCardSets[0]);
            return null;
        }
    }

    private static class UpdateCardAsyncTask extends AsyncTask<AvailableCardSets, Void, Void> {

        private CardDAO cardDao;

        private UpdateCardAsyncTask(CardDAO dao) {
            cardDao = dao;
        }

        @Override
        protected Void doInBackground(AvailableCardSets... availableCardSets) {
            cardDao.insert(availableCardSets[0]);
            return null;
        }
    }

    private static class DeleteCardAsyncTask extends AsyncTask<AvailableCardSets, Void, Void> {

        private CardDAO cardDao;

        private DeleteCardAsyncTask(CardDAO dao) {
            cardDao = dao;
        }

        @Override
        protected Void doInBackground(AvailableCardSets... availableCardSets) {
            cardDao.insert(availableCardSets[0]);
            return null;
        }
    }


    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {

        private CardDAO cardDao;

        private DeleteAllAsyncTask(CardDAO dao) {
            cardDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            cardDao.deleteAll();
            return null;
        }
    }


}
