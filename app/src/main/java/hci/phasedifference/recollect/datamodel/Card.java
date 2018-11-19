package hci.phasedifference.recollect.datamodel;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Card {
    private final String definition;
    private final String word;
    private int level;
    private boolean starred;

    @PrimaryKey(autoGenerate = true)
    private int id;

    public Card(String word, String definition, int level, boolean starred) {
        this.level = level;
        this.definition = definition;
        this.word = word;
        this.starred = starred;
    }

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
