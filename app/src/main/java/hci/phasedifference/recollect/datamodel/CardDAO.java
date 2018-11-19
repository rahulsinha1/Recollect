package hci.phasedifference.recollect.datamodel;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

@Dao
public interface CardDAO {

    @Insert
    void insert(Card card);

    @Update
    void update(Card card);

    @Delete
    void delete(Card card);

    @Query("DELETE FROM Card")
    void deleteAll();

    @Query("SELECT * FROM Card")
    LiveData<List<Card>> getAllCards();
}
