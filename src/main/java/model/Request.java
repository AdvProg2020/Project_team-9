package model;

public abstract class Request {
    private int id;

    public Request() {
    }
    public abstract void fulfill();
}
