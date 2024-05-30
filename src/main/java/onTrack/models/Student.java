package onTrack.models;

import java.util.List;

public class Student {

	    private String id;
	    private String name;
	    private List<Task> completedTasks;
	    private List<Task> totalTasks;

	    public Student(String id, String name, List<Task> completedTasks, List<Task> totalTasks) {
	        this.id = id;
	        this.name = name;
	        this.completedTasks = completedTasks;
	        this.totalTasks = totalTasks;
	    }

	    public String getId() {
	        return id;
	    }

	    public String getName() {
	        return name;
	    }

	    public List<Task> getCompletedTasks() {
	        return completedTasks;
	    }

	    public List<Task> getTotalTasks() {
	        return totalTasks;
	    }
}
