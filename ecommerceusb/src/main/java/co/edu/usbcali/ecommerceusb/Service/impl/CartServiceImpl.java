package co.edu.usbcali.ecommerceusb.Service.impl;

import co.edu.usbcali.ecommerceusb.Service.CartService;
import co.edu.usbcali.ecommerceusb.dto.CartResponse;
import co.edu.usbcali.ecommerceusb.dto.CreateCartRequest;
import co.edu.usbcali.ecommerceusb.dto.UpdateCartRequest;
import co.edu.usbcali.ecommerceusb.exception.BadRequestException;
import co.edu.usbcali.ecommerceusb.exception.InternalServerErrorException;
import co.edu.usbcali.ecommerceusb.exception.NotFoundException;
import co.edu.usbcali.ecommerceusb.mapper.CartMapper;
import co.edu.usbcali.ecommerceusb.model.Cart;
import co.edu.usbcali.ecommerceusb.model.User;
import co.edu.usbcali.ecommerceusb.repository.CartRepository;
import co.edu.usbcali.ecommerceusb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<CartResponse> getCarts() {
        List<Cart> list = cartRepository.findAll();
        if (list.isEmpty()) return List.of();
        return CartMapper.modelToCartResponseList(list);
    }

    @Override
    public CartResponse getCartById(Integer id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Debe ingresar el id para buscar");
        }
        return cartRepository.findById(id)
                .map(CartMapper::modelToCartResponse)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Carrito no encontrado con el id: %d", id)));
    }

    @Override
    public CartResponse createCart(CreateCartRequest request) {
        if (Objects.isNull(request)) {
            throw new BadRequestException("El objeto CreateCartRequest no puede ser nulo.");
        }
        if (request.getUserId() == null || request.getUserId() <= 0) {
            throw new BadRequestException("El campo userId debe ser mayor a 0.");
        }
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        try {
            Cart cart = CartMapper.createCartRequestToCart(request, user);
            cart = cartRepository.save(cart);
            return CartMapper.modelToCartResponse(cart);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error al guardar el carrito: " + e.getMessage());
        }
    }

    @Override
    public CartResponse updateCart(Integer id, UpdateCartRequest request) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Debe ingresar el id para actualizar");
        }
        if (Objects.isNull(request)) {
            throw new BadRequestException("El objeto UpdateCartRequest no puede ser nulo.");
        }
        if (Objects.isNull(request.getStatus()) || request.getStatus().isBlank()) {
            throw new BadRequestException("El campo status no puede ser nulo.");
        }
        Cart.CartStatus cartStatus;
        try {
            cartStatus = Cart.CartStatus.valueOf(request.getStatus());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("El status debe ser: ACTIVE, CHECKED_OUT o ABANDONED.");
        }
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Carrito no encontrado con el id: %d", id)));
        try {
            cart.setStatus(cartStatus);
            cart.setUpdatedAt(OffsetDateTime.now());
            cart = cartRepository.save(cart);
            return CartMapper.modelToCartResponse(cart);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error al actualizar el carrito: " + e.getMessage());
        }
    }

    @Override
    public void deleteCart(Integer id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Debe ingresar el id para eliminar");
        }
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Carrito no encontrado con el id: %d", id)));
        try {
            cartRepository.delete(cart);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error al eliminar el carrito: " + e.getMessage());
        }
    }
}