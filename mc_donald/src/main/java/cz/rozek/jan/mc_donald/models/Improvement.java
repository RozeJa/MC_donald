package cz.rozek.jan.mc_donald.models;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "improvements")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Improvement {
    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    private Double price;
    @DBRef
    private Map<String, Category> availableCategories = new TreeMap<>();
    private boolean available;
}
