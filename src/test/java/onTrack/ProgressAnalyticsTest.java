package onTrack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import onTrack.models.ProgressReport;
import onTrack.models.Student;
import onTrack.models.Task;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProgressAnalyticsTest {

    @Test
    public void testGenerateReportWithCompletedTasks() {
        Task task1 = new Task("1", "Task 1");
        Task task2 = new Task("2", "Task 2");
        List<Task> completedTasks = new ArrayList<>();
        completedTasks.add(task1);
        completedTasks.add(task2);
        Student student = new Student("1", "John Doe", completedTasks, completedTasks);
        ProgressAnalytics analytics = new ProgressAnalytics();
        ProgressReport report = analytics.generateReport(student);

        assertEquals(2, report.getCompletedTasks());
        assertEquals(2, report.getTotalTasks());
    }

    @Test
    public void testBoundaryConditions() {
        Task task = new Task("1", "Task 1");
        List<Task> oneTask = Collections.singletonList(task);
        Student student = new Student("1", "John Doe", oneTask, oneTask);
        ProgressAnalytics analytics = new ProgressAnalytics();
        ProgressReport report = analytics.generateReport(student);

        assertEquals(1, report.getCompletedTasks());
        assertEquals(1, report.getTotalTasks());

        List<Task> noTasks = Collections.emptyList();
        student = new Student("1", "John Doe", noTasks, noTasks);
        report = analytics.generateReport(student);

        assertEquals(0, report.getCompletedTasks());
        assertEquals(0, report.getTotalTasks());

        List<Task> maxTasks = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            maxTasks.add(new Task(String.valueOf(i), "Task " + i));
        }
        student = new Student("1", "John Doe", maxTasks, maxTasks);
        report = analytics.generateReport(student);

        assertEquals(1000, report.getCompletedTasks());
        assertEquals(1000, report.getTotalTasks());
    }

    @Test
    public void testInverseRelationships() {
        Task task1 = new Task("1", "Task 1");
        Task task2 = new Task("2", "Task 2");
        List<Task> totalTasks = new ArrayList<>();
        totalTasks.add(task1);
        List<Task> completedTasks = new ArrayList<>();
        completedTasks.add(task1);
        completedTasks.add(task2);

        Student student = new Student("1", "John Doe", completedTasks, totalTasks);
        ProgressAnalytics analytics = new ProgressAnalytics();

        assertThrows(IllegalArgumentException.class, () -> {
            analytics.generateReport(student);
        });
    }

    @Test
    public void testCrossCheckWithOtherTests() {
        Task task1 = new Task("1", "Task 1");
        Task task2 = new Task("2", "Task 2");
        List<Task> completedTasks = new ArrayList<>();
        completedTasks.add(task1);
        completedTasks.add(task2);
        Student student = new Student("1", "John Doe", completedTasks, completedTasks);

        ProgressAnalytics analytics = new ProgressAnalytics();
        ProgressReport report = analytics.generateReport(student);

        assertEquals(student.getCompletedTasks().size(), report.getCompletedTasks());
        assertEquals(student.getTotalTasks().size(), report.getTotalTasks());
    }

    @Test
    public void testErrorConditions() {
        ProgressAnalytics analytics = new ProgressAnalytics();

        assertThrows(NullPointerException.class, () -> {
            analytics.generateReport(null);
        });

        Student invalidStudent = new Student(null, null, null, null);
        assertThrows(NullPointerException.class, () -> {
            analytics.generateReport(invalidStudent);
        });
    }

    @Test
    public void testPerformanceCharacteristics() {
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            tasks.add(new Task(String.valueOf(i), "Task " + i));
        }
        Student student = new Student("1", "John Doe", tasks, tasks);

        ProgressAnalytics analytics = new ProgressAnalytics();
        long startTime = System.currentTimeMillis();
        ProgressReport report = analytics.generateReport(student);
        long endTime = System.currentTimeMillis();

        assertEquals(10000, report.getCompletedTasks());
        assertEquals(10000, report.getTotalTasks());
        assert (endTime - startTime) < 1000; // The function should execute in less than 1 second
    }
    
    @Test
    public void testGenerateReportWithMaximumTasks() {
        Task[] tasks = new Task[1000];
        for (int i = 0; i < 1000; i++) {
            tasks[i] = new Task(String.valueOf(i), "Task " + i);
        }
        Student student = new Student("1", "John Doe", Arrays.asList(tasks), Arrays.asList(tasks));
        ProgressAnalytics analytics = new ProgressAnalytics();
        ProgressReport report = analytics.generateReport(student);

        assertEquals(1000, report.getCompletedTasks());
        assertEquals(1000, report.getTotalTasks());
    }

    @Test
    public void testGenerateReportWithMixedTaskStates() {
        Task task1 = new Task("1", "Task 1");
        Task task2 = new Task("2", "Task 2");
        Task task3 = new Task("3", "Task 3");
        Student student = new Student("1", "John Doe", Arrays.asList(task1, task3), Arrays.asList(task1, task2, task3));
        ProgressAnalytics analytics = new ProgressAnalytics();
        ProgressReport report = analytics.generateReport(student);

        assertEquals(2, report.getCompletedTasks());
        assertEquals(3, report.getTotalTasks());
    }
    
    @Test
    public void testGenerateReportForLargeNumberOfTasks() {
        List<Task> tasks = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            tasks.add(new Task(String.valueOf(i), "Task " + i));
        }
        Student student = new Student("1", "John Doe", tasks, tasks);
        ProgressAnalytics analytics = new ProgressAnalytics();
        long startTime = System.currentTimeMillis();
        ProgressReport report = analytics.generateReport(student);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        assertTrue("Performance test failed. Duration: " + duration, duration < 1000);
        assertEquals(1000, report.getCompletedTasks());
        assertEquals(1000, report.getTotalTasks());
    }
}
