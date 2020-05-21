package com.es.core.model.user;

public class Achievement {
    private String id;
    private String name;
    private String description;
    private Long levelPoints;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getLevelPoints() {
        return levelPoints;
    }

    public void setLevelPoints(Long levelPoints) {
        this.levelPoints = levelPoints;
    }
}
