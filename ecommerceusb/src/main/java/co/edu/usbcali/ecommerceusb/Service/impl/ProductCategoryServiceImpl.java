package co.edu.usbcali.ecommerceusb.Service.impl;

import co.edu.usbcali.ecommerceusb.Service.ProductCategoryService;
import co.edu.usbcali.ecommerceusb.dto.CreateProductCategoryRequest;
import co.edu.usbcali.ecommerceusb.dto.ProductCategoryResponse;
import co.edu.usbcali.ecommerceusb.exception.BadRequestException;
import co.edu.usbcali.ecommerceusb.exception.InternalServerErrorException;
import co.edu.usbcali.ecommerceusb.exception.NotFoundException;
import co.edu.usbcali.ecommerceusb.mapper.ProductCategoryMapper;
import co.edu.usbcali.ecommerceusb.model.Category;
import co.edu.usbcali.ecommerceusb.model.Product;
import co.edu.usbcali.ecommerceusb.model.ProductCategory;
import co.edu.usbcali.ecommerceusb.repository.CategoryRepository;
import co.edu.usbcali.ecommerceusb.repository.ProductCategoryRepository;
import co.edu.usbcali.ecommerceusb.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<ProductCategoryResponse> getProductCategories() {
        List<ProductCategory> list = productCategoryRepository.findAll();
        if (list.isEmpty()) return List.of();
        return ProductCategoryMapper.modelToProductCategoryResponseList(list);
    }

    @Override
    public ProductCategoryResponse getProductCategoryById(Integer id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Debe ingresar el id para buscar");
        }
        return productCategoryRepository.findById(id)
                .map(ProductCategoryMapper::modelToProductCategoryResponse)
                .orElseThrow(() -> new NotFoundException(
                        String.format("ProductCategory no encontrado con el id: %d", id)));
    }

    @Override
    public ProductCategoryResponse createProductCategory(CreateProductCategoryRequest request) {
        if (Objects.isNull(request)) {
            throw new BadRequestException("El objeto CreateProductCategoryRequest no puede ser nulo.");
        }
        if (request.getProductId() == null || request.getProductId() <= 0) {
            throw new BadRequestException("El campo productId debe ser mayor a 0.");
        }
        if (request.getCategoryId() == null || request.getCategoryId() <= 0) {
            throw new BadRequestException("El campo categoryId debe ser mayor a 0.");
        }
        if (productCategoryRepository.existsByProductIdAndCategoryId(
                request.getProductId(), request.getCategoryId())) {
            throw new InternalServerErrorException("Ya existe esa relación producto-categoría.");
        }
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada"));
        try {
            ProductCategory pc = ProductCategoryMapper.createRequestToProductCategory(product, category);
            pc = productCategoryRepository.save(pc);
            return ProductCategoryMapper.modelToProductCategoryResponse(pc);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error al guardar la relación producto-categoría: " + e.getMessage());
        }
    }

    @Override
    public void deleteProductCategory(Integer id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Debe ingresar el id para eliminar");
        }
        ProductCategory pc = productCategoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("ProductCategory no encontrado con el id: %d", id)));
        try {
            productCategoryRepository.delete(pc);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error al eliminar la relación producto-categoría: " + e.getMessage());
        }
    }
}