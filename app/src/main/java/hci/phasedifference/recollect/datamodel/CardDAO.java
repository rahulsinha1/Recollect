package hci.phasedifference.recollect.datamodel;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

@Dao
public interface CardDAO {

    @Insert
    void insert(AvailableCardSets availableCardSets);

    @Update
    void update(AvailableCardSets availableCardSets);

    @Delete
    void delete(AvailableCardSets availableCardSets);

    @Query("DELETE FROM AvailableCardSets")
    void deleteAll();

    @Query("SELECT * FROM AvailableCardSets")
    LiveData<List<AvailableCardSets>> getAllCards();
}
