package cn.lannooo.entity.model;

import cn.lannooo.entity.database.UserHistory;

/**
 * Created by 51499 on 2017/5/28 0028.
 */
public class UserHistoryModel {
    private int hid;
    private int review;
    private NewsTypeModel type;
    public UserHistoryModel(UserHistory history){
        hid=history.getHistory_id();
        review=history.getReview_count();
        type = new NewsTypeModel(history.getNewsType());
    }

    public int getHid() {
        return hid;
    }

    public void setHid(int hid) {
        this.hid = hid;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public NewsTypeModel getType() {
        return type;
    }

    public void setType(NewsTypeModel type) {
        this.type = type;
    }
}
