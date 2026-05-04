package co.edu.usbcali.ecommerceusb.Service.impl;

import co.edu.usbcali.ecommerceusb.Service.CartItemService;
import co.edu.usbcali.ecommerceusb.dto.CartItemResponse;
import co.edu.usbcali.ecommerceusb.dto.CreateCartItemRequest;
import co.edu.usbcali.ecommerceusb.mapper.CartItemMapper;
import co.edu.usbcali.ecommerceusb.model.Cart;
import co.edu.usbcali.ecommerceusb.model.CartItem;
import co.edu.usbcali.ecommerceusb.model.Product;
import co.edu.usbcali.ecommerceusb.repository.CartItemRepository;
import co.edu.usbcali.ecommerceusb.repository.CartRepository;
import co.edu.usbcali.ecommerceusb.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<CartItemResponse> getCartItems() {
        List<CartItem> list = cartItemRepository.findAll();
        if (list.isEmpty()) return List.of();
        return CartItemMapper.modelToCartItemResponseList(list);
    }

    @Override
    public CartItemResponse getCartItemById(Integer id) throws Exception {
        if (id == null || id <= 0) {
            throw new Exception("Debe ingresar el id para buscar");
        }
        CartItem item = cartItemRepository.findById(id)
                .orElseThrow(() -> new Exception(
                        String.format("CartItem no encontrado con el id: %d", id)));
        return CartItemMapper.modelToCartItemResponse(item);
    }

    @Override
    public CartItemResponse createCartItem(CreateCartItemRequest request) throws Exception {
        if (Objects.isNull(request)) {
            throw new Exception("El objeto CreateCartItemRequest no puede ser nulo.");
        }
        if (request.getCartId() == null || request.getCartId() <= 0) {
            throw new Exception("El campo cartId debe ser mayor a 0.");
        }
        if (request.getProductId() == null || request.getProductId() <= 0) {
            throw new Exception("El campo productId debe ser mayor a 0.");
        }
        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new Exception("El campo quantity debe ser mayor a 0.");
        }
        if (cartItemRepository.existsByCartIdAndProductId(request.getCartId(), request.getProductId())) {
            throw new Exception("El producto ya existe en este carrito.");
        }

        Cart cart = cartRepository.findById(request.getCartId())
                .orElseThrow(() -> new Exception("Carrito no encontrado"));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new Exception("Producto no encontrado"));

        CartItem item = CartItemMapper.createCartItemRequestToCartItem(request, cart, product);
        item = cartItemRepository.save(item);
        return CartItemMapper.modelToCartItemResponse(item);
    }
}