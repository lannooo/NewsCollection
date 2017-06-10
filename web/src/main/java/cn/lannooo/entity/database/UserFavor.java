package cn.lannooo.entity.database;

import cn.lannooo.entity.database.NewsType;
import cn.lannooo.entity.database.User;

import javax.persistence.*;

/**
 * Created by 51499 on 2017/5/13 0013.
 */
@Entity
@Table(name = "UserFavor")
public class UserFavor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int favor_id;
    @Column(nullable = false)
    private boolean isValid;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id",nullable = false)
    private NewsType newsType;

    public UserFavor(){
        this.isValid = true;
    }

    public UserFavor(boolean isValid) {
        this.isValid = isValid;
    }

    public int getFavor_id() {
        return favor_id;
    }

    public void setFavor_id(int favor_id) {
        this.favor_id = favor_id;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
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
