package onTrack;

import onTrack.models.Resource;
import onTrack.models.Task;

import java.util.ArrayList;
import java.util.List;

public class ResourceRecommendation {
    public List<Resource> recommendResources(Task task) {
        List<Resource> resources = new ArrayList<>();
        if (task.getDescription().contains("Java")) {
            resources.add(new Resource("Java Programming Guide"));
        }
        if (task.getDescription().contains("Python")) {
            resources.add(new Resource("Python Programming Guide"));
        }
        return resources;
    }
}