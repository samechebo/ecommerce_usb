package co.edu.usbcali.ecommerceusb.Service;

import co.edu.usbcali.ecommerceusb.dto.CartItemResponse;
import co.edu.usbcali.ecommerceusb.dto.CreateCartItemRequest;
import co.edu.usbcali.ecommerceusb.dto.UpdateCartItemRequest;

import java.util.List;

public interface CartItemService {
    List<CartItemResponse> getCartItems();
    CartItemResponse getCartItemById(Integer id);
    CartItemResponse createCartItem(CreateCartItemRequest request);
    CartItemResponse updateCartItem(Integer id, UpdateCartItemRequest request);
    void deleteCartItem(Integer id);
}