package cz.rozek.jan.mc_donald.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import cz.rozek.jan.mc_donald.models.Order;

public interface OrderRepository extends MongoRepository<Order, String> {
    
}
