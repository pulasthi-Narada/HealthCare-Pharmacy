package com.healthcarepharmacy.orders;

public class order {

    private String addressText;
    private String name;
    private String phoneNumber;
    private String total;
    private String OrderAt;

    public order(String addressText, String name, String phoneNumber, String total, String orderAt) {
        this.addressText = addressText;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.total = total;
        OrderAt = orderAt;
    }

    public order() {
    }

    public String getAddressText() {
        return addressText;
    }

    public void setAddressText(String addressText) {
        this.addressText = addressText;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getOrderAt() {
        return OrderAt;
    }

    public void setOrderAt(String orderAt) {
        OrderAt = orderAt;
    }
}
