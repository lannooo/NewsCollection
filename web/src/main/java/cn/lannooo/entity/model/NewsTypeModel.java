package cn.lannooo.entity.model;

import cn.lannooo.entity.database.NewsType;

/**
 * Created by 51499 on 2017/5/21 0021.
 */
public class NewsTypeModel {
    private int id;
    private String name;
    private int total;
    private String website;
    private int click;

    public NewsTypeModel(NewsType type){
        this.id = type.getType_id();
        this.name = type.getType_name();
        this.total = type.getType_total();
        this.website = type.getType_website();
        this.click = type.getType_click();
    }

    public NewsTypeModel(int id, String name, int total, String website, int click) {
        this.id = id;
        this.name = name;
        this.total = total;
        this.website = website;
        this.click = click;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        this.click = click;
    }
}
