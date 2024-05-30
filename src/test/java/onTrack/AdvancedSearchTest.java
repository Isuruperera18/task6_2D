package onTrack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import onTrack.models.Task;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdvancedSearchTest {

    @Test
    public void testSearchTasksWithKeyword() {
        Task task1 = new Task("1", "This is a task about Java", "This is a task about Java");
        Task task2 = new Task("2", "This is another task", "This is another task");
        AdvancedSearch search = new AdvancedSearch();
        List<Task> tasks = Arrays.asList(task1, task2);

        List<Task> result = search.searchTasks(tasks, "Java");

        assertEquals(1, result.size());
        assertEquals("This is a task about Java", result.get(0).getDescription());
    }

    @Test
    public void testBoundaryConditions() {
        AdvancedSearch search = new AdvancedSearch();
        List<Task> emptyList = new ArrayList<>();
        List<Task> result = search.searchTasks(emptyList, "Java");

        assertEquals(0, result.size());

        Task taskWithEdgeKeyword = new Task("1", "JavaPython", "JavaPython");
        List<Task> tasks = List.of(taskWithEdgeKeyword);
        result = search.searchTasks(tasks, "Java");

        assertEquals(1, result.size());
        assertEquals("JavaPython", result.get(0).getDescription());
    }

    @Test
    public void testInverseRelationships() {
        AdvancedSearch search = new AdvancedSearch();
        Task task1 = new Task("1", "This is a task about Java", "This is a task about Java");
        Task task2 = new Task("2", "This is another task", "This is another task");
        List<Task> tasks = Arrays.asList(task1, task2);

        List<Task> result = search.searchTasks(tasks, "Ruby");

        assertEquals(0, result.size());
    }

    @Test
    public void testCrossCheckWithOtherTests() {
        AdvancedSearch search = new AdvancedSearch();
        Task task1 = new Task("1", "This is a task about Java", "This is a task about Java");
        Task task2 = new Task("2", "This is a task about Python", "This is a task about Python");
        Task task3 = new Task("3", "This is another task", "This is another task");
        List<Task> tasks = Arrays.asList(task1, task2, task3);

        List<Task> resultJava = search.searchTasks(tasks, "Java");
        List<Task> resultPython = search.searchTasks(tasks, "Python");

        assertEquals("This is a task about Java", resultJava.get(0).getDescription());
        assertEquals("This is a task about Python", resultPython.get(0).getDescription());
    }

    @Test
    public void testErrorConditions() {
        AdvancedSearch search = new AdvancedSearch();

        assertThrows(NullPointerException.class, () -> {
            search.searchTasks(null, "Java");
        });

        Task invalidTask = new Task(null, null, null);
        List<Task> tasks = List.of(invalidTask);
        assertThrows(NullPointerException.class, () -> {
            search.searchTasks(tasks, "Java");
        });
    }

    @Test
    public void testPerformanceCharacteristics() {
        AdvancedSearch search = new AdvancedSearch();
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            tasks.add(new Task(String.valueOf(i), "This is a task about Java", "This is a task about Java"));
        }

        long startTime = System.currentTimeMillis();
        List<Task> result = search.searchTasks(tasks, "Java");
        long endTime = System.currentTimeMillis();

        assertEquals(1000, result.size());
        assert (endTime - startTime) < 2000; // The search should complete in less than 2 seconds
    }
    
    @Test
    public void testSearchTasksWithCaseInsensitiveKeyword() {
        Task task1 = new Task("1", "This is a task about Java", "This is a task about Java");
        Task task2 = new Task("2", "This is another task", "This is another task");
        AdvancedSearch search = new AdvancedSearch();
        List<Task> tasks = Arrays.asList(task1, task2);

        List<Task> result = search.searchTasks(tasks, "java");

        assertEquals(1, result.size());
        assertEquals("This is a task about Java", result.get(0).getDescription());
    }

    @Test
    public void testSearchTasksWithSpecialCharactersInKeyword() {
        Task task1 = new Task("1", "This is a task about Java!", "This is a task about Java!");
        Task task2 = new Task("2", "This is another task", "This is another task");
        AdvancedSearch search = new AdvancedSearch();
        List<Task> tasks = Arrays.asList(task1, task2);

        List<Task> result = search.searchTasks(tasks, "Java!");

        assertEquals(1, result.size());
        assertEquals("This is a task about Java!", result.get(0).getDescription());
    }

    @Test
    public void testSearchTasksWithLargeNumberOfTasks() {
        List<Task> tasks = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            tasks.add(new Task(String.valueOf(i), "Task " + i + " about Java", "Task " + i + " about Java"));
        }
        AdvancedSearch search = new AdvancedSearch();
        long startTime = System.currentTimeMillis();
        List<Task> result = search.searchTasks(tasks, "Java");
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        assertTrue("Performance test failed. Duration: " + duration, duration < 1000);
        assertEquals(1000, result.size());
    }
}