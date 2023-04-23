package cz.rozek.jan.mc_donald.models;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInOrder {
    private int count;
    @DBRef
    private Product product;
    @DBRef
    private List<Improvement> improvements;
}
