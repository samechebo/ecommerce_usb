package co.edu.usbcali.ecommerceusb.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class InventoryResponse {
    private Integer id;
    private Integer productId;
    private String productName;
    private Integer stock;
}