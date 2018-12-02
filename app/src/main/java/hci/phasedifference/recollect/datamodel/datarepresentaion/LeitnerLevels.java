package hci.phasedifference.recollect.datamodel.datarepresentaion;

public enum LeitnerLevels {
    NEW_WORD("Showing a New Word"), MASTERED("Mastered"),
    REVIEWING1("Relearning, try to remember!"),
    REVIEWING2("Revising, get this right one more time"),
    REVIEWING3("Well Done! you are about to master this word");

    String value;

    LeitnerLevels(String s) {
        value = s;
    }


    @Override
    public String toString() {
        return value;
    }
}
