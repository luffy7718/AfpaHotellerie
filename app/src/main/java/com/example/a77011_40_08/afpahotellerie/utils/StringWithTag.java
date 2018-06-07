package com.example.a77011_40_08.afpahotellerie.utils;

public class StringWithTag {
    public String string;
    public Object tag;

    public StringWithTag(String string, Object tag) {
        this.string = string;
        this.tag = tag;
    }

    @Override
    public String toString() {
        return string;
    }
}
