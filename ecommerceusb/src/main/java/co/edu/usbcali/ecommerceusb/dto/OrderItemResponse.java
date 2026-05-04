package co.edu.usbcali.ecommerceusb.dto;

import lombok.Builder;
import lombok.Getter;
import java.math.BigDecimal;

@Builder
@Getter
public class OrderItemResponse {
    private Integer id;
    private Integer orderId;
    private Integer productId;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPriceSnapshot;
    private BigDecimal lineTotal;
}