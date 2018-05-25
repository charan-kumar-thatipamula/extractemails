package com.charan.csv;

public enum HeaderIndex {
    TITLE(0),
    AUTHOR(1),
    EMAIL(2);

    private final int position;
    HeaderIndex(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
