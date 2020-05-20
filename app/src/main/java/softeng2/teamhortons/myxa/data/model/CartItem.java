package softeng2.teamhortons.myxa.data.model;

import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;

public class CartItem {
    private String name;
    private String imgUriPreview;
    private double price;
    private int quantity;

    public CartItem() {
    }

    public CartItem(String name, String imgUriPreview, double price, int quantity) {
        this.name = name;
        this.imgUriPreview = imgUriPreview;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUriPreview() {
        return imgUriPreview;
    }

    public void setImgUriPreview(String imgUriPreview) {
        this.imgUriPreview = imgUriPreview;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
