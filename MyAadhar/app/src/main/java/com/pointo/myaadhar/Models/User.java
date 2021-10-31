package com.pointo.myaadhar.Models;

public class User {
    public String name;
    public String phoneNumber;
    public String photo;
    public String address;
    public String dob;
    public String gender;

    public User(String name, String phoneNumber, String photo, String address, String dob, String gender) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.photo = photo;
        this.address = address;
        this.dob = dob;
        this.gender = gender;
    }
}
