package onTrack;

import onTrack.models.Task;
import java.util.List;
import java.util.stream.Collectors;

public class AdvancedSearch {
    public List<Task> searchTasks(List<Task> tasks, String keyword) {
        return tasks.stream()
                .filter(task -> task.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
}