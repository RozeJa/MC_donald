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

import cz.rozek.jan.mc_donald.models.Improvement;
import cz.rozek.jan.mc_donald.services.DuplicateKeyException;
import cz.rozek.jan.mc_donald.services.ImprovementService;
import cz.rozek.jan.mc_donald.services.ValidationException;

@RestController
@RequestMapping("/api/improvements")
public class ImprovementController {

    @Autowired
    private ImprovementService improvementService;

    @GetMapping("/")
    public ResponseEntity<List<Improvement>> getAll() {
        try {
            List<Improvement> improvements = improvementService.readAll().stream().filter(Improvement::isAvailable)
                    .toList();

            return new ResponseEntity<>(improvements, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Improvement> getOne(@PathVariable String id) {
        try {
            Improvement improvement = improvementService.read(id);

            if (!improvement.isAvailable())
                throw new NullPointerException();

            return new ResponseEntity<>(improvement, HttpStatus.OK);
        } catch (NoSuchElementException | NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Improvement> create(@RequestBody Improvement improvement) {
        try {
            Improvement i = improvementService.create(improvement);

            return new ResponseEntity<>(i, HttpStatus.OK);
        } catch (ValidationException | DuplicateKeyException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Improvement> update(@PathVariable String id, @RequestBody Improvement improvement) {
        try {
            Improvement i = improvementService.update(id, improvement);

            return new ResponseEntity<>(i, HttpStatus.OK);
        } catch (ValidationException | DuplicateKeyException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Improvement> delete(@PathVariable String id) {
        try {
            Improvement i = improvementService.delete(id);

            return new ResponseEntity<>(i, HttpStatus.OK);
        } catch (NoSuchElementException | NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/data_recovery/")
    public ResponseEntity<List<Improvement>> getRemoved() {
        try {
            List<Improvement> improvements = improvementService.readAll().stream().filter(c -> !c.isAvailable())
                    .toList();

            return new ResponseEntity<>(improvements, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
