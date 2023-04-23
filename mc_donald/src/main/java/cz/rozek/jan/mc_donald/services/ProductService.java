package cz.rozek.jan.mc_donald.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.rozek.jan.mc_donald.models.Product;
import cz.rozek.jan.mc_donald.repositories.ProductRepository;

@Service
public class ProductService implements CrudService<Product, String> {
    
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product create(Product product) throws ValidationException, DuplicateKeyException {
        validate(product);

        product.setId(null);
        Product p = productRepository.save(product);
        return p;
    }

    @Override
    public Product read(String id) {
        Product p = productRepository.findById(id).get();
        return p;
    }

    @Override
    public List<Product> readAll() {
        List<Product> products = productRepository.findAll();
        return products;
    }

    @Override
    public Product update(String id, Product product) throws ValidationException, DuplicateKeyException {
        validate(product);

        product.setId(id);

        Product updated = productRepository.save(product);
        return updated;
    }

    @Override
    public Product delete(String id) {
        Product p = productRepository.findById(id).get();
        p.setAvailable(false);
        
        productRepository.save(p);
        return p;
    }


    @Override
    public void validate(Product product) throws ValidationException, DuplicateKeyException {
        StringBuilder sb = new StringBuilder();
        
        if (product.getName().isBlank()) {
            sb.append("Category name mustn't be ''."); 
        }

        if (product.getPrice() < 0) {
            sb.append("Price mustn´t be negative.");
        }

        if (product.getCategory() == null) {
            sb.append("Category mustn´t be null.");
        }

        if (!sb.toString().isEmpty()) {
            throw new ValidationException(sb.toString());
        }

        if (productRepository.findByName(product.getName()) != null) 
            throw new DuplicateKeyException("Product name: " + product.getName() + " is already exists.");
    }
}