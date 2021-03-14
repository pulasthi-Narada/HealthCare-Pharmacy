package com.healthcarepharmacy.address;

public class Address {

    String name;
    String address;
    String city;
    String mail;
    String number;
    String province;

    public Address(String name, String address, String city, String mail, String number, String province) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.mail = mail;
        this.number = number;
        this.province = province;
    }

    public Address() {
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
