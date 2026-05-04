package co.edu.usbcali.ecommerceusb.controller;

import co.edu.usbcali.ecommerceusb.Service.CategoryService;
import co.edu.usbcali.ecommerceusb.dto.CategoryResponse;
import co.edu.usbcali.ecommerceusb.dto.CreateCategoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    public List<CategoryResponse> getAll() {
        return categoryService.getCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(categoryService.getCategoryById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CreateCategoryRequest request) throws Exception {
        return new ResponseEntity<>(categoryService.createCategory(request), HttpStatus.CREATED);
    }
}