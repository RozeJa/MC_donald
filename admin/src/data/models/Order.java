package data.models;

import java.util.List;

public class Order implements IData {
    private String id;
    private List<ProductInOrder> products;
    private boolean finished;
    private boolean available = true;

    public Order(String id, List<ProductInOrder> products, boolean finished, boolean available) {
        this.id = id;
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
