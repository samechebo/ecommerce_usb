package co.edu.usbcali.ecommerceusb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateCartItemRequest {
    private Integer cartId;
    private Integer productId;
    private Integer quantity;
}