package hci.phasedifference.recollect.datamodel;

import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import hci.phasedifference.recollect.datamodel.datarepresentaion.CardSetImpl;
import hci.phasedifference.recollect.datamodel.typeconverters.TypeConverterCardSet;

import java.util.ArrayList;
import java.util.List;


@Entity
public class AvailableCardSets {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @TypeConverters(TypeConverterCardSet.class)
    private List<CardSetImpl> localsets;

    @Ignore
    public AvailableCardSets() {
        localsets = new ArrayList<>();
    }

    public AvailableCardSets(List<CardSetImpl> localsets) {
        System.out.println("Avinash" + localsets);
        this.localsets = localsets;
    }

    public List<CardSetImpl> getLocalsets() {
        return localsets;
    }

    public void setLocalsets(List<CardSetImpl> localsets) {
        this.localsets = localsets;
    }

    public void addCardSet(CardSetImpl set) {
        localsets.add(set);
    }

    public List<CardSetImpl> getAvailableCardSets() {
        return getListCopy(localsets);
    }

    private List<CardSetImpl> getListCopy(List<CardSetImpl> orig) {
        List listToReturn = new ArrayList<>();
        for (CardSetImpl c : orig) {
            listToReturn.add(new CardSetImpl(c));
        }
        return listToReturn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public String toString() {
        return localsets.stream()
                .map(b -> b.toString())
                .reduce("", (a, b) -> a + b);
    }
}
