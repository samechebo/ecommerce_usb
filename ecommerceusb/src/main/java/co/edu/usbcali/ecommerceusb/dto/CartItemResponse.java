package co.edu.usbcali.ecommerceusb.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CartItemResponse {
    private Integer id;
    private Integer cartId;
    private Integer productId;
    private String productName;
    private Integer quantity;
}