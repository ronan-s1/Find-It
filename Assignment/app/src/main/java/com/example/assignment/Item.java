package com.example.assignment;

public class Item {
    //attributes
    private int item_id;
    private String item;
    private String name;
    private String description;
    private String studentNo;
    private String phone;
    private String location;
    private String postTime;
    private String LF;

    //constructor
    public Item(int item_id, String item, String name, String description, String studentNo, String phone, String location, String postTime, String LF) {
        this.item_id = item_id;
        this.item = item;
        this.name = name;
        this.description = description;
        this.studentNo = studentNo;
        this.phone = phone;
        this.location = location;
        this.postTime = postTime;
        this.LF = LF;
    }

    //getters and setters
    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getLF() {
        return LF;
    }

    public String getLocation() {
        return location;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setLF(String LF) {
        this.LF = LF;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

}
