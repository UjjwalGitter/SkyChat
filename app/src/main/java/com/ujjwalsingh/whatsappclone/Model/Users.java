package com.ujjwalsingh.whatsappclone.Model;

public class Users {
    private String id;
    private String imgUrl;
    private String username;
    private String status;
    private String search;

    public Users(String id, String imgUrl, String username,String status,String search) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.username = username;
        this.status=status;
        this.search=search;
    }

    public Users() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
