package co.edu.usbcali.ecommerceusb.Service;

import co.edu.usbcali.ecommerceusb.dto.CreateProductCategoryRequest;
import co.edu.usbcali.ecommerceusb.dto.ProductCategoryResponse;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategoryResponse> getProductCategories();
    ProductCategoryResponse getProductCategoryById(Integer id) throws Exception;
    ProductCategoryResponse createProductCategory(CreateProductCategoryRequest request) throws Exception;
    void deleteProductCategory(Integer id) throws Exception;
}
