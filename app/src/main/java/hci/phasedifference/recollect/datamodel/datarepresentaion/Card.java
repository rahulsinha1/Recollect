package hci.phasedifference.recollect.datamodel.datarepresentaion;

import androidx.annotation.NonNull;

public class Card {

    private final String definition;
    private final String word;
    private final LeitnerLevels level;
    private final boolean starred;

    public Card(String word, String definition, LeitnerLevels level, boolean starred) {
        this.level = level;
        this.definition = definition;
        this.word = word;
        this.starred = starred;
    }

    public Card(Card other) {
        this.level = other.level;
        this.definition = other.definition;
        this.word = other.word;
        this.starred = other.starred;
    }

    public LeitnerLevels getLevel() {
        return level;
    }

    Card setLevel(LeitnerLevels level) {
        return new Card(word, definition, level, starred);
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

    Card setStarred(boolean starred) {
        return new Card(word, definition, level, starred);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Card)) {
            return false;
        }
        return (word.equals(((Card) obj).word) &&
                definition.equals(((Card) obj).definition));
    }

    @Override
    public int hashCode() {
        return (definition + word).hashCode();
    }

    @NonNull
    @Override
    public String toString() {
        return "Word: " + word + " defn: " + definition;
    }
}

