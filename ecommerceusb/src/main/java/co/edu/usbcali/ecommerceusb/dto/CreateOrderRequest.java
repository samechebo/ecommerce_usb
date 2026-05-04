package co.edu.usbcali.ecommerceusb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class CreateOrderRequest {
    private Integer userId;
    private BigDecimal totalAmount;
    private String currency;
}