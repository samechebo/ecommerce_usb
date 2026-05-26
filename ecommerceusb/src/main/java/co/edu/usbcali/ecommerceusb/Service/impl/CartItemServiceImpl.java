package co.edu.usbcali.ecommerceusb.Service.impl;

import co.edu.usbcali.ecommerceusb.Service.CartItemService;
import co.edu.usbcali.ecommerceusb.dto.CartItemResponse;
import co.edu.usbcali.ecommerceusb.dto.CreateCartItemRequest;
import co.edu.usbcali.ecommerceusb.dto.UpdateCartItemRequest;
import co.edu.usbcali.ecommerceusb.exception.BadRequestException;
import co.edu.usbcali.ecommerceusb.exception.InternalServerErrorException;
import co.edu.usbcali.ecommerceusb.exception.NotFoundException;
import co.edu.usbcali.ecommerceusb.mapper.CartItemMapper;
import co.edu.usbcali.ecommerceusb.model.Cart;
import co.edu.usbcali.ecommerceusb.model.CartItem;
import co.edu.usbcali.ecommerceusb.model.Product;
import co.edu.usbcali.ecommerceusb.repository.CartItemRepository;
import co.edu.usbcali.ecommerceusb.repository.CartRepository;
import co.edu.usbcali.ecommerceusb.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
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
    public CartItemResponse getCartItemById(Integer id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Debe ingresar el id para buscar");
        }
        return cartItemRepository.findById(id)
                .map(CartItemMapper::modelToCartItemResponse)
                .orElseThrow(() -> new NotFoundException(
                        String.format("CartItem no encontrado con el id: %d", id)));
    }

    @Override
    public CartItemResponse createCartItem(CreateCartItemRequest request) {
        if (Objects.isNull(request)) {
            throw new BadRequestException("El objeto CreateCartItemRequest no puede ser nulo.");
        }
        if (request.getCartId() == null || request.getCartId() <= 0) {
            throw new BadRequestException("El campo cartId debe ser mayor a 0.");
        }
        if (request.getProductId() == null || request.getProductId() <= 0) {
            throw new BadRequestException("El campo productId debe ser mayor a 0.");
        }
        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new BadRequestException("El campo quantity debe ser mayor a 0.");
        }
        if (cartItemRepository.existsByCartIdAndProductId(request.getCartId(), request.getProductId())) {
            throw new InternalServerErrorException("El producto ya existe en este carrito.");
        }
        Cart cart = cartRepository.findById(request.getCartId())
                .orElseThrow(() -> new NotFoundException("Carrito no encontrado"));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));
        try {
            CartItem item = CartItemMapper.createCartItemRequestToCartItem(request, cart, product);
            item = cartItemRepository.save(item);
            return CartItemMapper.modelToCartItemResponse(item);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error al guardar el item del carrito: " + e.getMessage());
        }
    }

    @Override
    public CartItemResponse updateCartItem(Integer id, UpdateCartItemRequest request) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Debe ingresar el id para actualizar");
        }
        if (Objects.isNull(request)) {
            throw new BadRequestException("El objeto UpdateCartItemRequest no puede ser nulo.");
        }
        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new BadRequestException("El campo quantity debe ser mayor a 0.");
        }
        CartItem item = cartItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("CartItem no encontrado con el id: %d", id)));
        try {
            item.setQuantity(request.getQuantity());
            item.setUpdatedAt(OffsetDateTime.now());
            item = cartItemRepository.save(item);
            return CartItemMapper.modelToCartItemResponse(item);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error al actualizar el item del carrito: " + e.getMessage());
        }
    }

    @Override
    public void deleteCartItem(Integer id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Debe ingresar el id para eliminar");
        }
        CartItem item = cartItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("CartItem no encontrado con el id: %d", id)));
        try {
            cartItemRepository.delete(item);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error al eliminar el item del carrito: " + e.getMessage());
        }
    }
}