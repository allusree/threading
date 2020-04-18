package com.menlo.concurrent.pojo;

public class Status {
    String name;
    String status;

    public Status(String name, String status) {
        super();
        this.name = name;
        this.status = status;
    }

    @Override
    public String toString() {
        return name + " is " + status;
    }
}
