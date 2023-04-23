package cz.rozek.jan.mc_donald.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import cz.rozek.jan.mc_donald.models.Improvement;

public interface ImprovementRepository extends MongoRepository<Improvement, String> {
    Improvement findByName(String name);
}
