package com.example.ivan.recyclerviewmap.Gasolino;

/**
 * Created by Ivan on 9/10/2017.
 */

public class User {

    private String firstName;
    private String secondName;
    private String email;
    private String password;
    private String password2;
    private int age;
    private String fuel;

    public User(String firstName, String secondName, String email, String password, String password2, int age, String fuel) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.password = password;
        this.password2 = password2;
        this.age = age;
        this.fuel = fuel;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPassword2() {
        return password2;
    }

    public int getAge() {
        return age;
    }

    public String getFuel() {
        return fuel;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }
}
