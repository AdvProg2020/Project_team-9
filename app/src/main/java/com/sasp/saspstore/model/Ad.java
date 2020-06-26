package com.sasp.saspstore.model;

public class Ad {
    private String content;

    public Ad(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }
}
