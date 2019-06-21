package tran.example.ppmtool.constants.projecttask;

public enum ProjectTaskPriority {
    HIGH(1),
    MEDIUM(2),
    LOW(3);

    private final int value;

    ProjectTaskPriority(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
