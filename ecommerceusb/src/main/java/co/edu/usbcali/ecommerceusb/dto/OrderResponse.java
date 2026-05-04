package co.edu.usbcali.ecommerceusb.dto;

import lombok.Builder;
import lombok.Getter;
import java.math.BigDecimal;

@Builder
@Getter
public class OrderResponse {
    private Integer id;
    private Integer userId;
    private String userFullName;
    private String status;
    private BigDecimal totalAmount;
    private String currency;
}