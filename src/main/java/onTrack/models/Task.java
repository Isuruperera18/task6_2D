package onTrack.models;

public class Task {

    private String id;
    private String content;
    private String description;

    public Task(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public Task(String id, String content, String description) {
        this.id = id;
        this.content = content;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getDescription() {
        return description;
    }
	}
