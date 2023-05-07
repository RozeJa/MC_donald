package cz.rozek.jan.admin_mc_donald.data.models;

import java.util.List;

public class ProductInOrder {
    private int count;
    private Product product;
    private List<Improvement> improvements;

    public ProductInOrder(int count, Product product, List<Improvement> improvements) {
        this.count = count;
        this.product = product;
        this.improvements = improvements;
    }

    public ProductInOrder() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Improvement> getImprovements() {
        return improvements;
    }

    public void setImprovements(List<Improvement> improvements) {
        this.improvements = improvements;
    }

    public double countPrice() {
        double price = product.getPrice();

        for (Improvement improvement : improvements) {
            price += improvement.getPrice();
        }

        return price;   
    }
}
