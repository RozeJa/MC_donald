package cz.rozek.jan.mc_donald.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    private String bgImgURI = ""; // TODO přidat do edit app 
    private boolean available;
}
