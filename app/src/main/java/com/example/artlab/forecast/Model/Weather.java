package com.example.artlab.forecast.Model;

/**
 * Created by artlab on 17. 7. 22.
 */

public class Weather {
    private int id;
    private String main;
    private String description;
    private String icon;

    public Weather(int id, String main, String description, String icon){
        this.id = id;
        this.description = description;
        this.icon = icon;
        this.main = main;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
