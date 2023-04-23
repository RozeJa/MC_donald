package cz.rozek.jan.mc_donald.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.rozek.jan.mc_donald.models.Category;
import cz.rozek.jan.mc_donald.repositories.CategoryRepository;

@Service
public class CategoryService implements CrudService<Category, String> {
    
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category create(Category category) throws ValidationException, DuplicateKeyException {
        validate(category);

        if (categoryRepository.findByName(category.getName()) != null) 
            throw new DuplicateKeyException("Category name: " + category.getName() + " is already exists.");

        category.setId(null);
        Category c = categoryRepository.save(category);
        return c;
    }

    @Override
    public Category read(String id) {
        Category c = categoryRepository.findById(id).get();
        return c;
    }

    @Override
    public List<Category> readAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

    @Override
    public Category update(String id, Category category) throws ValidationException, DuplicateKeyException {
        validate(category);

        category.setId(id);

        Category updated = categoryRepository.save(category);
        return updated;
    }

    @Override
    public Category delete(String id) {
        Category c = categoryRepository.findById(id).get();
        c.setAvailable(false);

        categoryRepository.save(c);
        return c;
    }


    @Override
    public void validate(Category category) throws ValidationException, DuplicateKeyException {
        StringBuilder sb = new StringBuilder();
        
        if (category.getName().isBlank()) {
            sb.append("Category name mustn't be ''."); 
        }

        if (!sb.toString().isEmpty()) {
            throw new ValidationException(sb.toString());
        }

        if (categoryRepository.findByName(category.getName()) != null) 
            throw new DuplicateKeyException("Category name: " + category.getName() + " is already exists.");
    }
}
