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

import cz.rozek.jan.mc_donald.models.Product;
import cz.rozek.jan.mc_donald.services.DuplicateKeyException;
import cz.rozek.jan.mc_donald.services.ProductService;
import cz.rozek.jan.mc_donald.services.ValidationException;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public ResponseEntity<List<Product>> getAll() {
        try {

            List<Product> products = productService.readAll().stream().filter(Product::isAvailable).toList();

            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getOne(@PathVariable String id) {
        try {
            Product product = productService.read(id);

            if (!product.isAvailable())
                throw new NullPointerException();

            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (NoSuchElementException | NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Product> create(@RequestBody Product product) {
        try {
            Product p = productService.create(product);

            return new ResponseEntity<>(p, HttpStatus.OK);
        } catch (ValidationException | DuplicateKeyException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable String id, @RequestBody Product product) {
        try {
            Product p = productService.update(id, product);

            return new ResponseEntity<>(p, HttpStatus.OK);
        } catch (ValidationException | DuplicateKeyException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(@PathVariable String id) {
        try {
            Product p = productService.delete(id);

            return new ResponseEntity<>(p, HttpStatus.OK);
        } catch (NoSuchElementException | NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/data_recovery/")
    public ResponseEntity<List<Product>> getRemoved() {
        try {
            List<Product> products = productService.readAll().stream().filter(p -> !p.isAvailable()).toList();

            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
