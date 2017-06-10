package cn.lannooo.entity.database;

import javax.persistence.*;

/**
 * Created by 51499 on 2017/4/7 0007.
 */
@Entity
@Table(name="News")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int news_id;
    @Column(nullable = false)
    private String news_title;
    @Column(nullable = false)
    private String news_url;
    @Column
    private String news_source;
    @Column
    private String news_time;
    @Column(nullable = false)
    private String news_content;
    @Column(nullable = false)
    private int news_click;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id", nullable = false)
    private NewsType newsType;

    public News(){}

    public News(String news_title, String news_url, String news_source, String news_time, String news_content, int news_click) {
        this.news_title = news_title;
        this.news_url = news_url;
        this.news_source = news_source;
        this.news_time = news_time;
        this.news_content = news_content;
        this.news_click = news_click;
    }

    public News(String news_title, String news_url, String news_source, String news_time, String news_content, int news_click, NewsType newsType) {
        this.news_title = news_title;
        this.news_url = news_url;
        this.news_source = news_source;
        this.news_time = news_time;
        this.news_content = news_content;
        this.news_click = news_click;
        this.newsType = newsType;
    }

    public void updateClick(){
        news_click++;
    }

    public NewsType getNewsType() {
        return newsType;
    }

    public void setNewsType(NewsType newsType) {
        this.newsType = newsType;
    }

    public int getNews_id() {
        return news_id;
    }

    public void setNews_id(int news_id) {
        this.news_id = news_id;
    }

    public String getNews_title() {
        return news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public String getNews_url() {
        return news_url;
    }

    public void setNews_url(String news_url) {
        this.news_url = news_url;
    }

    public String getNews_source() {
        return news_source;
    }

    public void setNews_source(String news_source) {
        this.news_source = news_source;
    }

    public String getNews_time() {
        return news_time;
    }

    public void setNews_time(String news_time) {
        this.news_time = news_time;
    }

    public String getNews_content() {
        return news_content;
    }

    public void setNews_content(String news_content) {
        this.news_content = news_content;
    }

    public int getNews_click() {
        return news_click;
    }

    public void setNews_click(int news_click) {
        this.news_click = news_click;
    }
}
