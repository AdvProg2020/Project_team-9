package com.sasp.saspstore.model;

public class Request {
    private String id;

    public Request(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void fulfill() {}
}