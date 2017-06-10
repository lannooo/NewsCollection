package cn.lannooo.entity.database;

import cn.lannooo.entity.database.NewsType;
import cn.lannooo.entity.database.User;

import javax.persistence.*;

/**
 * Created by 51499 on 2017/5/13 0013.
 */
@Entity
@Table(name = "UserHistory")
public class UserHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int history_id;
    @Column(nullable = false)
    private int review_count;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id", nullable = false)
    private NewsType newsType;

    public UserHistory(){
        this.review_count = 0;
    }

    public UserHistory(int review_count) {
        this.review_count = review_count;
    }

    public void updateReview(){
        this.review_count++;
    }

    public int getHistory_id() {
        return history_id;
    }

    public void setHistory_id(int history_id) {
        this.history_id = history_id;
    }

    public int getReview_count() {
        return review_count;
    }

    public void setReview_count(int review_count) {
        this.review_count = review_count;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public NewsType getNewsType() {
        return newsType;
    }

    public void setNewsType(NewsType newsType) {
        this.newsType = newsType;
    }
}
