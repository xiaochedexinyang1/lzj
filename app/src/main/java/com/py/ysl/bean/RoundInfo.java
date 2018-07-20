package com.py.ysl.bean;

import java.util.List;

/**
 * 自定义view的实体类
 */
public class RoundInfo {
    private String color;
    private String name;
    private String count;
    private int prent;

    public int getPrent() {
        return prent;
    }

    public void setPrent(int prent) {
        this.prent = prent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
