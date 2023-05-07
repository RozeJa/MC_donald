package cz.rozek.jan.admin_mc_donald.data.models;

import java.util.Map;
import java.util.TreeMap;

public class Product implements IData {
    private String id;
    private String name;
    private Double price = null;
    private Category category;    
    private Map<String, Improvement> availableImprovements = new TreeMap<>();
    private boolean available = true; 

    public Product(String id, String name, Double price, Category category, Map<String, Improvement> availableImprovements,
            boolean available) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.availableImprovements = availableImprovements;
        this.available = available;
    }

    public Product() {
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Map<String, Improvement> getAvailableImprovements() {
        return availableImprovements;
    }

    public void setAvailableImprovements(Map<String, Improvement> availableImprovements) {
        this.availableImprovements = availableImprovements;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void addImprovement(Improvement i) {
        availableImprovements.put(i.getId(), i);
    }

    public void removeImprovement(Improvement i) {
        availableImprovements.remove(i.getId());
    }

    public void removeImprovements() {
        availableImprovements = new TreeMap<>();
    }
}
