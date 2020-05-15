package model;

public abstract class Request {
    private String id;

    public Request() {
    }

    public String getId() {
        return id;
    }

    public abstract void fulfill();
}
