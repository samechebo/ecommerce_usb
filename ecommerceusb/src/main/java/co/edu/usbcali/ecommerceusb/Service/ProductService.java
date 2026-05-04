package co.edu.usbcali.ecommerceusb.Service;

import co.edu.usbcali.ecommerceusb.dto.CreateProductRequest;
import co.edu.usbcali.ecommerceusb.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getProducts();
    ProductResponse getProductById(Integer id) throws Exception;
    ProductResponse createProduct(CreateProductRequest request) throws Exception;
}