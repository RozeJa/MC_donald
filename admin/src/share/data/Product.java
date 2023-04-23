package share.data;

import java.util.Set;
import java.util.TreeSet;

public class Product extends AData implements Comparable<Product> {
    private static final long serialVersionUID = 42L;
    private Set<Improvement> improvements = new TreeSet<>();
    private Category category;
    private String name;
    private double price;

    public Product(int id, String name, double price, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;

    }
    public Product() {}
  
    public Set<Improvement> getImprovements() {
        return improvements;
    }
    
    public void setImprovements(Set<Improvement> improvements) {
        this.improvements = improvements;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void removeImprovements() {
        improvements = new TreeSet<>();
    }

    public void addImprovement(Improvement i) {
        if (i.getCategories().contains(category))
            improvements.add(i);
    } 

    @Override
    public boolean equals(Object obj)  {

        if (obj instanceof Product) {
            Product b = (Product) obj;
            return this.name.equals(b.name);
        } 

        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public int compareTo(Product o) {
        return this.name.compareTo(o.name);
    }
    @Override
    public String toString() {
        return this.name;
    }
}
