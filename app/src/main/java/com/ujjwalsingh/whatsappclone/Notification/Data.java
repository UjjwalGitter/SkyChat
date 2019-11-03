package com.ujjwalsingh.whatsappclone.Notification;

public class Data {
    private String user;
    private String body;
    private String titile;
    private String sented;
    private int icon;

    public Data() {
    }

    public Data(String user, String body, String titile, String sented, int icon) {
        this.user = user;
        this.body = body;
        this.titile = titile;
        this.sented = sented;
        this.icon = icon;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }

    public String getSented() {
        return sented;
    }

    public void setSented(String sented) {
        this.sented = sented;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
