package cz.rozek.jan.mc_donald.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import cz.rozek.jan.mc_donald.models.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
    Product findByName(String name);
}
