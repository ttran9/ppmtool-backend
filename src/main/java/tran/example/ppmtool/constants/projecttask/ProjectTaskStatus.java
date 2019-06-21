package tran.example.ppmtool.constants.projecttask;

public enum ProjectTaskStatus {
    TO_DO("TO_DO");

    private final String status;

    ProjectTaskStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
