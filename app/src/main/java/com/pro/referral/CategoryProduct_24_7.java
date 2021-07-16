package com.pro.referral;

public class CategoryProduct_24_7  {

    String image_url,image_url2,big_des,price;
    String name;
    String desc,id,song_url;

    public CategoryProduct_24_7(String image_url, String name,String desc,String image_url2,String big_des,String price) {
        this.image_url = image_url;
        this.name = name;
        this.desc=desc;
        this.image_url2=image_url2;
        this.big_des=big_des;
        this.price=price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url2() {
        return image_url2;
    }

    public void setImage_url2(String image_url2) {
        this.image_url2 = image_url2;
    }

    public String getBig_des() {
        return big_des;
    }

    public void setBig_des(String big_des) {
        this.big_des = big_des;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
