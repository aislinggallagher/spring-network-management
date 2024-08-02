package com.example.springproject;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Node {
    private String name;
    private String location;
    private int latitude;
    private int longitude;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    public Node() {
    }

    public Node(String name, String location, int latitude, int longitude) {
        this.name = name;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getid() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getLatitude() {
        return latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }
}
