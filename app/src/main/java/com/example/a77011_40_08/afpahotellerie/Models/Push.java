package com.example.a77011_40_08.afpahotellerie.Models;

public class Push {

    int status;
    String data;
    String type;

    public Push() {

    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString(){
        return "[status:" + this.getStatus() + ", type:" + this.getType() + ", data:" + this.getData() + "]";
    }
}
