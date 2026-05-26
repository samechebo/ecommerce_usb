package co.edu.usbcali.ecommerceusb.Service;

import co.edu.usbcali.ecommerceusb.dto.CategoryResponse;
import co.edu.usbcali.ecommerceusb.dto.CreateCategoryRequest;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getCategories();
    CategoryResponse getCategoryById(Integer id);
    CategoryResponse createCategory(CreateCategoryRequest request);
    CategoryResponse updateCategory(Integer id, CreateCategoryRequest request);
    void deleteCategory(Integer id);
}