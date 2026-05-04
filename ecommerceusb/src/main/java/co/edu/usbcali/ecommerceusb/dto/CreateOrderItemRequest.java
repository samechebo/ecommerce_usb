package co.edu.usbcali.ecommerceusb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class CreateOrderItemRequest {
    private Integer orderId;
    private Integer productId;
    private Integer quantity;
    private BigDecimal unitPriceSnapshot;
}