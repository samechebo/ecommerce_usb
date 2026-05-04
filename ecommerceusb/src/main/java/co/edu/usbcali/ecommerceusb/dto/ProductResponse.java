package co.edu.usbcali.ecommerceusb.dto;

import lombok.Builder;
import lombok.Getter;
import java.math.BigDecimal;

@Builder
@Getter
public class ProductResponse {
    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean available;
}
