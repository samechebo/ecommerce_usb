package co.edu.usbcali.ecommerceusb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateCategoryRequest {
    private String name;
    private Integer parentId;
}
