package com.pro.referral;

public class Highlight_product  {

    //String image_url;
    String name;
    String amount;

    public Highlight_product(String name, String amount) {
        //  this.image_url = image_url;
        this.name = name;
        this.amount=amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
