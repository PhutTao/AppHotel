package com.example.apphotel.Searching.Domain;

import java.io.Serializable;

public class LastSearchDomain implements Serializable {
    private String name;

    public LastSearchDomain(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
