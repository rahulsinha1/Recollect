package hci.phasedifference.recollect.datamodel;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {AvailableCardSets.class}, version = 1)
public abstract class CardDataBase extends RoomDatabase {

    private static CardDataBase instance;
    private static RoomDatabase.Callback roomcallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new CreateDBAsyncTast(instance).execute();
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

    private static class CreateDBAsyncTast extends AsyncTask<Void, Void, Void> {

        private CardDAO dao;

        private CreateDBAsyncTast(CardDataBase db) {
            dao = db.cardDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.insert(new AvailableCardSets("Word1", "Definition1", 0, false));
            dao.insert(new AvailableCardSets("Word2", "Definition2", 0, false));
            dao.insert(new AvailableCardSets("Word3", "Definition3", 0, false));

            return null;
        }
    }
}
