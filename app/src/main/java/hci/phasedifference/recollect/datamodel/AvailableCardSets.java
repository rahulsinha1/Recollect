package hci.phasedifference.recollect.datamodel;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import hci.phasedifference.recollect.datamodel.typeconverters.TypeConverterCardSet;

import java.util.ArrayList;
import java.util.List;

@Entity
public class AvailableCardSets {
    private final String definition;
    private final String word;
    private int level;
    private boolean starred;

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    @TypeConverters(TypeConverterCardSet.class)
    private List<String> titles;

    public AvailableCardSets(String word, String definition, int level, boolean starred) {
        this.level = level;
        this.definition = definition;
        this.word = word;
        this.starred = starred;
        titles = new ArrayList<>();
    }


    @PrimaryKey(autoGenerate = true)
    private int id;


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getDefinition() {
        return definition;
    }

    public String getWord() {
        return word;
    }

    public boolean isStarred() {
        return starred;
    }

    public void setStarred(boolean starred) {
        this.starred = starred;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
