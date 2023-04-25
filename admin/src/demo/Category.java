package demo;

public class Category {
    
    private String id;
    private String name;
    private boolean available;
    
    public Category() {
    }
    public Category(String name, boolean available) {
        this.name = name;
        this.available = available;
    }
    public Category(String id, String name, boolean available) {
        this.id = id;
        this.name = name;
        this.available = available;
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
    public boolean isAvailable() {
        return available;
    }
    public void setAvailable(boolean available) {
        this.available = available;
    }
}
