package com.nbkakkad.chatapp.model;

/**
 * Created by NABEEL-EXPOSE on 03-Jan-18.
 */

public class Model {
    private String title, desc, time;
    private Integer img1;

    public Model() {
    }

    public Model(Integer img1, Integer img2, String title, String desc, String time) {
        this.img1=img1;
        this.time=time;
        this.title=title;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getImg1() {
        return img1;
    }

    public void setImg1(Integer img1) {
        this.img1 = img1;
    }


}
