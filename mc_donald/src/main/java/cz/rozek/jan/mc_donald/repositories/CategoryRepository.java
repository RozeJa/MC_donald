package cz.rozek.jan.mc_donald.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import cz.rozek.jan.mc_donald.models.Category;

public interface CategoryRepository extends MongoRepository<Category, String> {
    Category findByName(String name);
}
