package com.dtp.bachusiki.REST;

public class Bacchus {
    private final Long id;
    private String name;
    private String location;
    private String author;
    private String sponsor;
    private String date;

    public Bacchus(Long id, String name, String location, String author, String sponsor, String date) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.author = author;
        this.sponsor = sponsor;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
