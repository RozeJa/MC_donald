package data.models;

import java.util.Map;
import java.util.TreeMap;

public class Improvement implements IData {
    private String id;

    private String name;
    private Double price;
    private Map<String, Category> availableCategories = new TreeMap<>();
    private boolean available = true;

    public Improvement(String id, String name, Double price, Map<String, Category> availableCategories, boolean available) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.availableCategories = availableCategories;
        this.available = available;
    }

    public Improvement() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Map<String, Category> getAvailableCategories() {
        return availableCategories;
    }

    public void setAvailableCategories(Map<String, Category> availableCategories) {
        this.availableCategories = availableCategories;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void removeCategories() {
        availableCategories = new TreeMap<>();        
    }
}
