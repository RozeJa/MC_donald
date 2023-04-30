package cz.rozek.jan.mc_donald.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cz.rozek.jan.mc_donald.models.Category;
import cz.rozek.jan.mc_donald.services.CategoryService;
import cz.rozek.jan.mc_donald.services.DuplicateKeyException;
import cz.rozek.jan.mc_donald.services.ValidationException;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<List<Category>> getAll() {
        try {

            List<Category> categories = categoryService.readAll().stream().filter(Category::isAvailable).toList();

            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getOne(@PathVariable String id) {
        try {
            Category category = categoryService.read(id);

            if (!category.isAvailable())
                throw new NullPointerException();

            return new ResponseEntity<>(category, HttpStatus.OK);
        } catch (NoSuchElementException | NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Category> create(@RequestBody Category category) {
        try {
            Category c = categoryService.create(category);

            return new ResponseEntity<>(c, HttpStatus.OK);
        } catch (ValidationException | DuplicateKeyException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable String id, @RequestBody Category category) {
        try {
            Category c = categoryService.update(id, category);

            return new ResponseEntity<>(c, HttpStatus.OK);
        } catch (ValidationException | DuplicateKeyException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> delete(@PathVariable String id) {
        try {
            Category c = categoryService.delete(id);

            return new ResponseEntity<>(c, HttpStatus.OK);
        } catch (NoSuchElementException | NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/data_recovery/")
    public ResponseEntity<List<Category>> getRemoved() {
        try {
            List<Category> categories = categoryService.readAll().stream().filter(c -> !c.isAvailable()).toList();

            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
