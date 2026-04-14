package co.edu.usbcali.ecommerceusb.controller;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserResponse {

    private Integer id;
    private String fullName;
    private String email;
    private String documentTypeName;
    private Integer documentTypeId;
    private String documentNumber;

}
