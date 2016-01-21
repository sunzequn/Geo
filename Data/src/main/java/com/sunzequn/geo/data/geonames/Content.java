package com.sunzequn.geo.data.geonames;

/**
 * Created by Sloriac on 16/1/21.
 */
public class Content {

    private int id;
    private String content;

    public Content() {
    }

    public Content(int id, String content) {
        this.content = content;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Content{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
