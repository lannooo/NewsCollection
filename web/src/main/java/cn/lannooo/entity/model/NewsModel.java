package cn.lannooo.entity.model;

import cn.lannooo.entity.database.News;

/**
 * Created by 51499 on 2017/5/21 0021.
 */
public class NewsModel {
    private int id;
    private String title;
    private String url;
    private String source;
    private String time;
    private String content;
    private int click;
    private String type;

    public NewsModel(int id, String title, String url, String source, String time, String content, int click, String type) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.source = source;
        this.time = time;
        this.content = content;
        this.click = click;
        this.type = type;
    }
    public NewsModel(News news){
        this.id = news.getNews_id();
        this.title = news.getNews_title();
        this.url = news.getNews_url();
        this.source = news.getNews_source();
        this.time = news.getNews_time();
        this.content = news.getNews_content();
        this.click = news.getNews_click();
        this.type = news.getNewsType().getType_name();
    }

    public NewsModel(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        this.click = click;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
