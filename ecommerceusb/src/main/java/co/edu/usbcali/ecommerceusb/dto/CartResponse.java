package co.edu.usbcali.ecommerceusb.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CartResponse {
    private Integer id;
    private Integer userId;
    private String userFullName;
    private String status;
}
