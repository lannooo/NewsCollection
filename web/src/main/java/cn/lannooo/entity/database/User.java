package cn.lannooo.entity.database;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 51499 on 2017/5/13 0013.
 */
@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int user_id;
    @Column(nullable = false)
    private String user_name;
    @Column(nullable = false)
    private String user_password;
    @Column(nullable = false)
    private String user_email;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<UserHistory> histories = new HashSet<>();

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private Set<UserFavor> favors = new HashSet<>();

    public User(){}

    public User(String user_name, String user_password, String user_email) {
        this.user_name = user_name;
        this.user_password = user_password;
        this.user_email = user_email;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
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
