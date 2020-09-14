package com.geek.rabbit.console.producer;

import lombok.Data;


public class Article {
    private String theme;
    private String title;

    public Article(final String theme, final String title) {
        this.theme = theme;
        this.title = title;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(final String theme) {
        this.theme = theme;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Article{" +
               "theme='" + theme + '\'' +
               ", title='" + title + '\'' +
               '}';
    }
}
