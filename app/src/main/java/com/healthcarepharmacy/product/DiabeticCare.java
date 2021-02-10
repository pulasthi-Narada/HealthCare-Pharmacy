package com.healthcarepharmacy.product;

public class DiabeticCare {
    private String name;
    private String type;
    private String price;
    private String image;

    public DiabeticCare() {

    }

    public DiabeticCare(String name, String type, String price, String image) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
