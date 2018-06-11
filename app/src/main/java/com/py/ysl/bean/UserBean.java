package com.py.ysl.bean;


public class UserBean{
    private String name;
    private int age;
    private String code;
    private boolean isMan;
    private String inToDate;
    public UserBean( String name,int age,String code,boolean isMan,String inToDate){
        this.name=name;
        this.age = age;
        this.code = code;
        this.isMan = isMan;
        this.inToDate = inToDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isMan() {
        return isMan;
    }

    public void setMan(boolean man) {
        isMan = man;
    }

    public String getInToDate() {
        return inToDate;
    }

    public void setInToDate(String inToDate) {
        this.inToDate = inToDate;
    }
}
