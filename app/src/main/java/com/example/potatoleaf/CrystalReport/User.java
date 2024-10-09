package com.example.potatoleaf.CrystalReport;



public class User {
    private String name;
    private String email;
    private String bloodGroup;
    private String dateOfBirth;

    // Constructor
    public User(String name, String email, String bloodGroup, String dateOfBirth) {
        this.name = name;
        this.email = email;
        this.bloodGroup = bloodGroup;
        this.dateOfBirth = dateOfBirth;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
}
}