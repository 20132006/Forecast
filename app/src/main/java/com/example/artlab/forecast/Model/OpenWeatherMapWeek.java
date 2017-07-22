package com.example.artlab.forecast.Model;

import java.util.List;

/**
 * Created by artlab on 17. 7. 22.
 */

public class OpenWeatherMapWeek {
    private City city;
    private int cod;
    private String message;
    private int cnt;
    private List<Days> list;


    public OpenWeatherMapWeek() {
    }

    public OpenWeatherMapWeek(City city, int cod, String message, int cnt, List<Days> list) {
        this.city = city;
        this.cod = cod;
        this.message = message;
        this.cnt = cnt;
        this.list = list;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public List<Days> getList() {
        return list;
    }

    public void setList(List<Days> list) {
        this.list = list;
    }
}
