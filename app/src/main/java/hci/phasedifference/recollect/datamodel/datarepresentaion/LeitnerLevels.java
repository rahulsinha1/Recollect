package hci.phasedifference.recollect.datamodel.datarepresentaion;

public enum LeitnerLevels {
    NEW_WORD("New Word"), MASTERED("Mastered"), REVIEWING1("Reviewing, will be shown twice"), REVIEWING2("Reviewing, will be shown once again");

    String value;

    LeitnerLevels(String s) {
        value = s;
    }
}
