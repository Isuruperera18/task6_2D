package onTrack.models;

public class ProgressReport {
    private int completedTasks;
    private int totalTasks;

    public ProgressReport(int completedTasks, int totalTasks) {
        this.completedTasks = completedTasks;
        this.totalTasks = totalTasks;
    }

    public int getCompletedTasks() {
        return completedTasks;
    }

    public int getTotalTasks() {
        return totalTasks;
    }
}