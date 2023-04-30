package data.models;

import java.util.List;

public class Order implements IData {
    private String id;
    private List<ProductInOrder> products;
    private boolean available = true;

    public Order(String id, List<ProductInOrder> products, boolean available) {
        this.id = id;
        this.products = products;
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

    public List<ProductInOrder> getProducts() {
        return products;
    }

    public void setProducts(List<ProductInOrder> products) {
        this.products = products;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
