package co.edu.usbcali.ecommerceusb.Service.impl;

import co.edu.usbcali.ecommerceusb.Service.ProductService;
import co.edu.usbcali.ecommerceusb.dto.CreateProductRequest;
import co.edu.usbcali.ecommerceusb.dto.ProductResponse;
import co.edu.usbcali.ecommerceusb.mapper.ProductMapper;
import co.edu.usbcali.ecommerceusb.model.Product;
import co.edu.usbcali.ecommerceusb.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductResponse> getProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) return List.of();
        return ProductMapper.modelToProductResponseList(products);
    }

    @Override
    public ProductResponse getProductById(Integer id) throws Exception {
        if (id == null || id <= 0) {
            throw new Exception("Debe ingresar el id para buscar");
        }
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new Exception(
                        String.format("Producto no encontrado con el id: %d", id)));
        return ProductMapper.modelToProductResponse(product);
    }

    @Override
    public ProductResponse createProduct(CreateProductRequest request) throws Exception {
        if (Objects.isNull(request)) {
            throw new Exception("El objeto CreateProductRequest no puede ser nulo.");
        }
        if (Objects.isNull(request.getName()) || request.getName().isBlank()) {
            throw new Exception("El campo name no puede ser nulo.");
        }
        if (Objects.isNull(request.getPrice()) || request.getPrice().doubleValue() <= 0) {
            throw new Exception("El campo price debe ser mayor a 0.");
        }
        if (productRepository.existsByName(request.getName())) {
            throw new Exception("Ya existe un producto con ese nombre.");
        }
        Product product = ProductMapper.createProductRequestToProduct(request);
        product = productRepository.save(product);
        return ProductMapper.modelToProductResponse(product);
    }
}
