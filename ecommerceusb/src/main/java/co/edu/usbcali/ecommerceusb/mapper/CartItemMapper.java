package co.edu.usbcali.ecommerceusb.mapper;

import co.edu.usbcali.ecommerceusb.dto.CartItemResponse;
import co.edu.usbcali.ecommerceusb.dto.CreateCartItemRequest;
import co.edu.usbcali.ecommerceusb.model.Cart;
import co.edu.usbcali.ecommerceusb.model.CartItem;
import co.edu.usbcali.ecommerceusb.model.Product;

import java.time.OffsetDateTime;
import java.util.List;

public class CartItemMapper {

    public static CartItemResponse modelToCartItemResponse(CartItem item) {
        return CartItemResponse.builder()
                .id(item.getId())
                .cartId(item.getCart() != null ? item.getCart().getId() : null)
                .productId(item.getProduct() != null ? item.getProduct().getId() : null)
                .productName(item.getProduct() != null ? item.getProduct().getName() : null)
                .quantity(item.getQuantity())
                .build();
    }

    public static List<CartItemResponse> modelToCartItemResponseList(List<CartItem> list) {
        return list.stream().map(CartItemMapper::modelToCartItemResponse).toList();
    }

    public static CartItem createCartItemRequestToCartItem(
            CreateCartItemRequest request, Cart cart, Product product) {
        return CartItem.builder()
                .cart(cart)
                .product(product)
                .quantity(request.getQuantity())
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();
    }
}