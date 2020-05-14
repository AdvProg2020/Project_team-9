package model;

public abstract class Request {
    private int id;

    public int getId() {
        return id;
    }

    public Request() {
    }
    public abstract void fulfill();
}
