package share.data;

public class Category extends AData implements Comparable<Category> {
    private static final long serialVersionUID = 42L;
    private String name;

    public Category(int id, String name) {
        this.name = name;
        this.id = id;
    }
    public Category() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj)  {

        if (obj instanceof Category) {
            Category b = (Category) obj;
            return this.name.equals(b.name);
        } 

        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
    @Override
    public int compareTo(Category o) {
        return this.name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
