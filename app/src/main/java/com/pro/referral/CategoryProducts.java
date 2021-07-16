package com.pro.referral;

public class CategoryProducts  {

    String song_url;
    String name;
    String desc;
    String id;


    public CategoryProducts(String name, String desc, String id, String song_url) {
        this.name = name;
        this.desc=desc;
        this.id=id;
        this.song_url = song_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSong_url() {
        return song_url;
    }
}
