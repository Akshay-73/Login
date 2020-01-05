package com.elfstudio.login.RxJava_Retrofit.pojo;

public class Repo {

    private String name;
    private String description;
    private String fullName;
    private String updatedAt;

    public Repo(String name, String description, String fullName, String updatedAt) {
        this.name = name;
        this.description = description;
        this.fullName = fullName;
        this.updatedAt = updatedAt;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
