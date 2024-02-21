package org.example.controllers;

import lombok.AllArgsConstructor;
import org.example.entities.CategoryEntity;
import org.example.repositories.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<List<CategoryEntity>> index() {
        List<CategoryEntity> list = categoryRepository.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryEntity> addCategory(@RequestBody CategoryEntity category) {
        CategoryEntity savedCategory = categoryRepository.save(category);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryEntity> updateCategory(@PathVariable Long id, @RequestBody CategoryEntity updatedCategory) {
        Optional<CategoryEntity> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            CategoryEntity existingCategory = optionalCategory.get();
            existingCategory.setName(updatedCategory.getName());
            // You can update other fields as needed
            CategoryEntity savedCategory = categoryRepository.save(existingCategory);
            return new ResponseEntity<>(savedCategory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        if (categoryRepository.existsById(Math.toIntExact(id))) {
            categoryRepository.deleteById(Math.toIntExact(id));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
