package com.healthcarepharmacy.cart;

public class Cart {
    private String name;
    private String type;
    private String price;
    private String image;
    private String qty;

    public Cart() {
    }

    public Cart(String name, String type, String price, String image, String qty) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.image = image;
        this.qty = qty;
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

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
