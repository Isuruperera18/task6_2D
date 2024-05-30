package onTrack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import onTrack.models.Resource;
import onTrack.models.Task;
import org.junit.Test;

import java.util.List;
import java.util.ArrayList;

public class ResourceRecommendationTest {

    @Test
    public void testRecommendResourcesForJavaTask() {
        ResourceRecommendation recommendation = new ResourceRecommendation();
        Task task = new Task("1", "This is a task about Java", "This is a task about Java");

        List<Resource> resources = recommendation.recommendResources(task);

        assertEquals(1, resources.size());
        assertEquals("Java Programming Guide", resources.get(0).getName());
    }

    @Test
    public void testBoundaryConditions() {
        ResourceRecommendation recommendation = new ResourceRecommendation();
        Task emptyTask = new Task("1", "", "");
        List<Resource> resources = recommendation.recommendResources(emptyTask);

        assertEquals(0, resources.size());

        Task taskWithEdgeKeyword = new Task("2", "JavaPython", "JavaPython");
        resources = recommendation.recommendResources(taskWithEdgeKeyword);

        assertEquals(2, resources.size());
        assertEquals("Java Programming Guide", resources.get(0).getName());
        assertEquals("Python Programming Guide", resources.get(1).getName());
    }

    @Test
    public void testInverseRelationships() {
        ResourceRecommendation recommendation = new ResourceRecommendation();
        Task unrelatedTask = new Task("1", "This is a task about Ruby", "This is a task about Ruby");

        List<Resource> resources = recommendation.recommendResources(unrelatedTask);

        assertEquals(0, resources.size());
    }

    @Test
    public void testCrossCheckWithOtherTests() {
        ResourceRecommendation recommendation = new ResourceRecommendation();
        Task task = new Task("1", "This is a task about Java", "This is a task about Java");

        List<Resource> resources = recommendation.recommendResources(task);
        List<String> expectedResources = List.of("Java Programming Guide");

        assertEquals(expectedResources.size(), resources.size());
        for (int i = 0; i < expectedResources.size(); i++) {
            assertEquals(expectedResources.get(i), resources.get(i).getName());
        }
    }

    @Test
    public void testErrorConditions() {
        ResourceRecommendation recommendation = new ResourceRecommendation();

        assertThrows(NullPointerException.class, () -> {
            recommendation.recommendResources(null);
        });

        Task invalidTask = new Task(null, null, null);
        assertThrows(NullPointerException.class, () -> {
            recommendation.recommendResources(invalidTask);
        });
    }

    @Test
    public void testPerformanceCharacteristics() {
        ResourceRecommendation recommendation = new ResourceRecommendation();
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            tasks.add(new Task(String.valueOf(i), "This is a task about Java", "This is a task about Java"));
        }

        long startTime = System.currentTimeMillis();
        for (Task task : tasks) {
            recommendation.recommendResources(task);
        }
        long endTime = System.currentTimeMillis();

        assert (endTime - startTime) < 2000; // All recommendations should be generated in less than 2 seconds
    }
    
    @Test
    public void testRecommendResourcesForMultipleKeywords() {
        ResourceRecommendation recommendation = new ResourceRecommendation();
        Task task = new Task("3", "This is a task about Java and Python", "This is a task about Java and Python");

        List<Resource> resources = recommendation.recommendResources(task);

        assertEquals(2, resources.size());
        assertEquals("Java Programming Guide", resources.get(0).getName());
        assertEquals("Python Programming Guide", resources.get(1).getName());
    }

    @Test
    public void testNoResourcesForEmptyDescription() {
        ResourceRecommendation recommendation = new ResourceRecommendation();
        Task task = new Task("5", "This is a task", "");

        List<Resource> resources = recommendation.recommendResources(task);

        assertEquals(0, resources.size());
    }
    
    @Test
    public void testRecommendResourcesForLargeTaskDescription() {
        ResourceRecommendation recommendation = new ResourceRecommendation();
        StringBuilder description = new StringBuilder("This is a task about Java");
        for (int i = 0; i < 1000; i++) {
            description.append(" Java");
        }
        Task task = new Task("7", description.toString(), description.toString());

        long startTime = System.currentTimeMillis();
        List<Resource> resources = recommendation.recommendResources(task);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        assertTrue("Performance test failed. Duration: " + duration, duration < 1000);
        assertEquals(1, resources.size());
        assertEquals("Java Programming Guide", resources.get(0).getName());
    }
}
