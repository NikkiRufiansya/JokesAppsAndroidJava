package com.example.jokesappandroidjava.model;

public class JokesModel {
    private int id;
    private String type, setup, punchline;

    public JokesModel(int id, String type, String setup, String punchline) {
        this.id = id;
        this.type = type;
        this.setup = setup;
        this.punchline = punchline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSetup() {
        return setup;
    }

    public void setSetup(String setup) {
        this.setup = setup;
    }

    public String getPunchline() {
        return punchline;
    }

    public void setPunchline(String punchline) {
        this.punchline = punchline;
    }
}
