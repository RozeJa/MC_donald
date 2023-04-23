package share.data;

import java.util.Set;
import java.util.TreeSet;

public class Improvement extends AData implements Comparable<Improvement> {
    private static final long serialVersionUID = 42L;
    private Set<Category> categories = new TreeSet<>();

    private String name;
    private double price;

    public Improvement(int id, String name, double price) {
       this.id = id;
       this.name = name;
       this.price = price;
    }
    public Improvement() {}

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
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

    public void removeCategories() {
        categories = new TreeSet<>();
    }

    @Override
    public boolean equals(Object obj)  {

        if (obj instanceof Improvement) {
            Improvement b = (Improvement) obj;
            return this.name.equals(b.name);
        } 

        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public int compareTo(Improvement o) {
        return this.name.compareTo(o.name);
    }
    @Override
    public String toString() {
        return this.name;
    }
}
