package cz.rozek.jan.admin_mc_donald.data.models;

import java.util.List;

public class Order implements IData {
    private String id;
    private int number;
    private List<ProductInOrder> products;
    private boolean finished;
    private boolean available = true;

    public Order(String id, int number, List<ProductInOrder> products, boolean finished, boolean available) {
        this.id = id;
        this.number = number;
        this.products = products;
        this.finished = finished;
        this.available = available;
    }

    public Order() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<ProductInOrder> getProducts() {
        return products;
    }

    public void setProducts(List<ProductInOrder> products) {
        this.products = products;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
