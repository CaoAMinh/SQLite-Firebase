package com.example.cuoiky.model;

public class Student {
    private int id;
    private String name;
    private String address;
    private String email;
    private int id_class;

    public Student(int id, String name, String address, String email, int id_class) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.id_class = id_class;
    }

    public Student(int id, String name, String address, String email) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.id_class = 0;
    }

    public Student() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId_class() {
        return id_class;
    }

    public void setId_class(int id_class) {
        this.id_class = id_class;
    }
}
