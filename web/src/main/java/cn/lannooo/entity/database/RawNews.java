package cn.lannooo.entity.database;

import javax.persistence.*;

/**
 * Created by 51499 on 2017/5/13 0013.
 */
@Entity
@Table(name = "raw_news")
public class RawNews {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false, unique = true)
    private String url;
    @Column
    private String source;
    @Column(nullable = false)
    private String title;
    @Column
    private String time;
    @Column(nullable = false)
    private String content;
    @Column
    private String types;
    @Column(nullable = false)
    private String website;

    public RawNews(){}

    public RawNews(String url, String source, String title, String time, String content, String types, String website) {
        this.url = url;
        this.source = source;
        this.title = title;
        this.time = time;
        this.content = content;
        this.types = types;
        this.website = website;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
