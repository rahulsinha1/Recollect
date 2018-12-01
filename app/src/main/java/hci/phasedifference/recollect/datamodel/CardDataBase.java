package hci.phasedifference.recollect.datamodel;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import hci.phasedifference.recollect.datamodel.datarepresentaion.CardSetImpl;

import java.util.ArrayList;
import java.util.List;

@Database(entities = {AvailableCardSets.class}, version = 1)
public abstract class CardDataBase extends RoomDatabase {

    private static CardDataBase instance;
    private static RoomDatabase.Callback roomcallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new CreateDBAsyncTask(instance).execute();
        }
    };

    public static synchronized CardDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), CardDataBase.class, "card_database")
                    .addCallback(roomcallback)
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    public abstract CardDAO cardDao();

    private static class CreateDBAsyncTask extends AsyncTask<Void, Void, Void> {

        private CardDAO dao;

        private CreateDBAsyncTask(CardDataBase db) {
            dao = db.cardDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //todo: remove this once add set is implemented
            CardSetImpl set = new CardSetImpl("German");
            set.addCard("Der Tisch", "The table");
            set.addCard("Die Tasche", "The bag");
            set.addCard("Danke", "Thank you");


            CardSetImpl set1 = new CardSetImpl("French");
            set1.addCard("Merci", "The table");
            set1.addCard("Oui", "Yes");
            set1.addCard("Bonjour", "Hello");


            CardSetImpl set2 = new CardSetImpl("Spanish");
            set2.addCard("Gracias", "Thank you");
            set2.addCard("Si", "Yes");
            set2.addCard("Hola", "Hello");

            List<CardSetImpl> list = new ArrayList<>();
            list.add(set);
            list.add(set1);
            list.add(set2);

            System.out.println("avinash inserting " + list);
            dao.insert(new AvailableCardSets(list));

            return null;
        }
    }
}
