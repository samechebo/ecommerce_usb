package co.edu.usbcali.ecommerceusb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateCartRequest {
    private String status; // ACTIVE, CHECKED_OUT, ABANDONED
}