package cn.lannooo.entity.database;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 51499 on 2017/5/13 0013.
 */
@Entity
@Table(name = "NewsType")
public class NewsType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int type_id;

    @Column(nullable = false)
    private String type_name;

    @Column(nullable = false)
    private int type_total;

    @Column(nullable = false)
    private String type_website;

    @Column(nullable = false)
    private int type_click;

    @OneToMany(mappedBy = "newsType",fetch = FetchType.LAZY)
    private Set<News> newsList = new HashSet<>();

    @OneToMany(mappedBy = "newsType",fetch = FetchType.LAZY)
    private Set<UserHistory> histories = new HashSet<>();

    @OneToMany(mappedBy = "newsType",fetch = FetchType.LAZY)
    private Set<UserFavor> favors = new HashSet<>();

    public NewsType(){}

    public NewsType(String type_name, String type_website){
        this.type_name = type_name;
        this.type_total = 0;
        this.type_website = type_website;
        this.type_click = 0;
    }

    public NewsType(String type_name, int type_total, String type_website, int type_click) {
        this.type_name = type_name;
        this.type_total = type_total;
        this.type_website = type_website;
        this.type_click = type_click;
    }

    public void updateClick(){
        this.type_click++;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public int getType_total() {
        return type_total;
    }

    public void setType_total(int type_total) {
        this.type_total = type_total;
    }

    public String getType_website() {
        return type_website;
    }

    public void setType_website(String type_website) {
        this.type_website = type_website;
    }

    public int getType_click() {
        return type_click;
    }

    public void setType_click(int type_click) {
        this.type_click = type_click;
    }

    public Set<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(Set<News> newsList) {
        this.newsList = newsList;
    }

    public Set<UserHistory> getHistories() {
        return histories;
    }

    public void setHistories(Set<UserHistory> histories) {
        this.histories = histories;
    }

    public Set<UserFavor> getFavors() {
        return favors;
    }

    public void setFavors(Set<UserFavor> favors) {
        this.favors = favors;
    }
}
