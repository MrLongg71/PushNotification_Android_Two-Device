package com.example.push;

public class Article {
    public String title;
    public String author;
    private String iduser;
    public Article() {
        // Default constructor required for calls to DataSnapshot.getValue(Article.class)
    }

    public Article(String title, String author,String iduser) {
        this.title = title;
        this.author = author;
        this.iduser = iduser;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }
}
