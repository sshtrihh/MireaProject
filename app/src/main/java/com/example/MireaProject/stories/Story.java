package com.example.MireaProject.stories;

public class Story {
    private int count;
    private String story;

    public Story(int count, String story) {
        this.count = count;
        this.story = story;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }
}
