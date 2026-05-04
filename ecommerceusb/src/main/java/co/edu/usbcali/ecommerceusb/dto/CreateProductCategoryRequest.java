package co.edu.usbcali.ecommerceusb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateProductCategoryRequest {
    private Integer productId;
    private Integer categoryId;
}
