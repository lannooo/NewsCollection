package cn.lannooo.entity.model;

/**
 * Created by 51499 on 2017/5/22 0022.
 */
public class UserFavorModel {
    private int fid;
    private NewsTypeModel type;

    public UserFavorModel(int fid, NewsTypeModel type) {
        this.fid = fid;
        this.type = type;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public NewsTypeModel getType() {
        return type;
    }

    public void setType(NewsTypeModel type) {
        this.type = type;
    }
}
