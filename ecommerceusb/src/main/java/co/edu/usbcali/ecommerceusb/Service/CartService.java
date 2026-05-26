package co.edu.usbcali.ecommerceusb.Service;

import co.edu.usbcali.ecommerceusb.dto.CartResponse;
import co.edu.usbcali.ecommerceusb.dto.CreateCartRequest;
import co.edu.usbcali.ecommerceusb.dto.UpdateCartRequest;

import java.util.List;

public interface CartService {
    List<CartResponse> getCarts();
    CartResponse getCartById(Integer id);
    CartResponse createCart(CreateCartRequest request);
    CartResponse updateCart(Integer id, UpdateCartRequest request);
    void deleteCart(Integer id);
}