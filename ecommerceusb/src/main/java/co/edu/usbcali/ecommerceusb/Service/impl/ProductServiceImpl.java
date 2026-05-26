package co.edu.usbcali.ecommerceusb.Service.impl;

import co.edu.usbcali.ecommerceusb.Service.ProductService;
import co.edu.usbcali.ecommerceusb.dto.CreateProductRequest;
import co.edu.usbcali.ecommerceusb.dto.ProductResponse;
import co.edu.usbcali.ecommerceusb.exception.BadRequestException;
import co.edu.usbcali.ecommerceusb.exception.InternalServerErrorException;
import co.edu.usbcali.ecommerceusb.exception.NotFoundException;
import co.edu.usbcali.ecommerceusb.mapper.ProductMapper;
import co.edu.usbcali.ecommerceusb.model.Product;
import co.edu.usbcali.ecommerceusb.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
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
    public ProductResponse getProductById(Integer id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Debe ingresar el id para buscar");
        }
        return productRepository.findById(id)
                .map(ProductMapper::modelToProductResponse)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Producto no encontrado con el id: %d", id)));
    }

    @Override
    public ProductResponse createProduct(CreateProductRequest request) {
        if (Objects.isNull(request)) {
            throw new BadRequestException("El objeto CreateProductRequest no puede ser nulo.");
        }
        if (Objects.isNull(request.getName()) || request.getName().isBlank()) {
            throw new BadRequestException("El campo name no puede ser nulo.");
        }
        if (Objects.isNull(request.getPrice()) || request.getPrice().doubleValue() <= 0) {
            throw new BadRequestException("El campo price debe ser mayor a 0.");
        }
        if (productRepository.existsByName(request.getName())) {
            throw new InternalServerErrorException("Ya existe un producto con ese nombre.");
        }
        try {
            Product product = ProductMapper.createProductRequestToProduct(request);
            product = productRepository.save(product);
            return ProductMapper.modelToProductResponse(product);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error al guardar el producto: " + e.getMessage());
        }
    }

    @Override
    public ProductResponse updateProduct(Integer id, CreateProductRequest request) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Debe ingresar el id para actualizar");
        }
        if (Objects.isNull(request)) {
            throw new BadRequestException("El objeto CreateProductRequest no puede ser nulo.");
        }
        if (Objects.isNull(request.getName()) || request.getName().isBlank()) {
            throw new BadRequestException("El campo name no puede ser nulo.");
        }
        if (Objects.isNull(request.getPrice()) || request.getPrice().doubleValue() <= 0) {
            throw new BadRequestException("El campo price debe ser mayor a 0.");
        }
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Producto no encontrado con el id: %d", id)));
        if (!product.getName().equals(request.getName()) &&
                productRepository.existsByName(request.getName())) {
            throw new InternalServerErrorException("Ya existe un producto con ese nombre.");
        }
        try {
            product.setName(request.getName());
            product.setDescription(request.getDescription());
            product.setPrice(request.getPrice());
            product.setAvailable(request.getAvailable() != null
                    ? request.getAvailable() : product.getAvailable());
            product.setUpdatedAt(OffsetDateTime.now());
            product = productRepository.save(product);
            return ProductMapper.modelToProductResponse(product);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error al actualizar el producto: " + e.getMessage());
        }
    }

    @Override
    public void deleteProduct(Integer id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Debe ingresar el id para eliminar");
        }
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Producto no encontrado con el id: %d", id)));
        try {
            productRepository.delete(product);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error al eliminar el producto: " + e.getMessage());
        }
    }
}
