package hci.phasedifference.recollect.datamodel;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CardRepository {

    private CardDAO cardDao;
    private LiveData<List<Card>> allCards;

    public CardRepository(Application app) {

        CardDataBase carddb = CardDataBase.getInstance(app.getApplicationContext());
        cardDao = carddb.cardDao();
        allCards = cardDao.getAllCards();

    }


    void insert(Card card) {
        new InsertCardAsyncTask(cardDao).execute(card);

    }

    void update(Card card) {
        new UpdateCardAsyncTask(cardDao).execute(card);

    }

    void delete(Card card) {
        new DeleteCardAsyncTask(cardDao).execute(card);

    }

    void deleteAll() {
        new DeleteAllAsyncTask(cardDao).execute();
    }

    LiveData<List<Card>> getAllCards() {
        return allCards;
    }


    private static class InsertCardAsyncTask extends AsyncTask<Card, Void, Void> {

        private CardDAO cardDao;

        private InsertCardAsyncTask(CardDAO dao) {
            cardDao = dao;
        }

        @Override
        protected Void doInBackground(Card... cards) {
            cardDao.insert(cards[0]);
            return null;
        }
    }

    private static class UpdateCardAsyncTask extends AsyncTask<Card, Void, Void> {

        private CardDAO cardDao;

        private UpdateCardAsyncTask(CardDAO dao) {
            cardDao = dao;
        }

        @Override
        protected Void doInBackground(Card... cards) {
            cardDao.insert(cards[0]);
            return null;
        }
    }

    private static class DeleteCardAsyncTask extends AsyncTask<Card, Void, Void> {

        private CardDAO cardDao;

        private DeleteCardAsyncTask(CardDAO dao) {
            cardDao = dao;
        }

        @Override
        protected Void doInBackground(Card... cards) {
            cardDao.insert(cards[0]);
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
