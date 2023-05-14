package cz.rozek.jan.mc_donald.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    private String id;
    private int number;
    private List<ProductInOrder> products;
    private boolean finished = false;
    private boolean available = true;

    @Override
    public String toString() {
        // todo test
        double totalPrice = 0;

        StringBuilder sb = new StringBuilder();
            sb.append(String.format("Order: %s", getId()));

        for (ProductInOrder productInOrder : products) {
            sb.append(String.format("%n%dx %-40s  %10.2f Kč %10.2f Kč", productInOrder.getCount(), productInOrder.getProduct().getName(), productInOrder.getProduct().getPrice(), productInOrder.countPrice()));
            totalPrice += productInOrder.getProduct().getPrice() * productInOrder.getCount();

            for (Improvement improvement : productInOrder.getImprovements()) {
                sb.append(String.format("%n%10s%-40s %.2f Kč %10.2f Kč", "", improvement.getName(), improvement.getPrice(), improvement.getPrice() * productInOrder.getCount()));
                totalPrice += improvement.getPrice() * productInOrder.getCount();
            }
        }

        sb.append(String.format("%nCelkem: %.2f Kč", totalPrice));
        return sb.toString();
    }
}
