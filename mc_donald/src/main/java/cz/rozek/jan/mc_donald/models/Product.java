package cz.rozek.jan.mc_donald.models;

import java.util.Set;
import java.util.TreeSet;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    private Double price = null;
    @DBRef
    private Category category;
    @DBRef
    private Set<Improvement> availableImprovements = new TreeSet<>();
    private boolean available;
}
