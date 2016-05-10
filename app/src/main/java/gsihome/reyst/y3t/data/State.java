package gsihome.reyst.y3t.data;

public enum State {

    WAIT(0),
    IN_WORK(1),
    DONE(9);

    private int mStateValue;

    State(int mStateValue) {
        this.mStateValue = mStateValue;
    }

    public int getValue() {
        return mStateValue;
    }

    public static State getByValue(int value) {
        switch (value) {
            case 1: return IN_WORK;
            case 9: return DONE;
            default: return WAIT;
        }
    }

}
