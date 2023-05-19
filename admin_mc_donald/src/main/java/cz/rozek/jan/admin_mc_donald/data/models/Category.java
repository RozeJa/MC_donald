package cz.rozek.jan.admin_mc_donald.data.models;

public class Category implements IData {

    private String id;
    private String name;
    private String bgImgURI;
    private boolean available = true;

    public Category() {
    }

    public Category(String name, boolean available) {
        this.name = name;
        this.available = available;
    }

    public Category(String id, String name, String bgImgURI, boolean available) {
        this.id = id;
        this.name = name;
        this.bgImgURI = bgImgURI;
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

    public String getBgImgURI() {
        return bgImgURI;
    }

    public void setBgImgURI(String bgImgURI) {
        this.bgImgURI = bgImgURI;
    }
    
    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
