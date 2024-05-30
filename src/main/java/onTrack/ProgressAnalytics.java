package onTrack;

import onTrack.models.ProgressReport;
import onTrack.models.Student;

public class ProgressAnalytics {
    public ProgressReport generateReport(Student student) {
        int completedTasks = student.getCompletedTasks().size();
        int totalTasks = student.getTotalTasks().size();

        if (completedTasks > totalTasks) {
            throw new IllegalArgumentException("Completed tasks cannot exceed total tasks");
        }

        return new ProgressReport(completedTasks, totalTasks);
    }
}