package co.edu.usbcali.ecommerceusb.mapper;

import co.edu.usbcali.ecommerceusb.dto.CreateProductCategoryRequest;
import co.edu.usbcali.ecommerceusb.dto.ProductCategoryResponse;
import co.edu.usbcali.ecommerceusb.model.Category;
import co.edu.usbcali.ecommerceusb.model.Product;
import co.edu.usbcali.ecommerceusb.model.ProductCategory;

import java.util.List;

public class ProductCategoryMapper {

    public static ProductCategoryResponse modelToProductCategoryResponse(ProductCategory pc) {
        return ProductCategoryResponse.builder()
                .id(pc.getId())
                .productId(pc.getProduct() != null ? pc.getProduct().getId() : null)
                .productName(pc.getProduct() != null ? pc.getProduct().getName() : null)
                .categoryId(pc.getCategory() != null ? pc.getCategory().getId() : null)
                .categoryName(pc.getCategory() != null ? pc.getCategory().getName() : null)
                .build();
    }

    public static List<ProductCategoryResponse> modelToProductCategoryResponseList(List<ProductCategory> list) {
        return list.stream().map(ProductCategoryMapper::modelToProductCategoryResponse).toList();
    }

    public static ProductCategory createRequestToProductCategory(Product product, Category category) {
        return ProductCategory.builder()
                .product(product)
                .category(category)
                .build();
    }
}