package co.edu.usbcali.ecommerceusb.Service.impl;

import co.edu.usbcali.ecommerceusb.Service.CartService;
import co.edu.usbcali.ecommerceusb.dto.CartResponse;
import co.edu.usbcali.ecommerceusb.dto.CreateCartRequest;
import co.edu.usbcali.ecommerceusb.mapper.CartMapper;
import co.edu.usbcali.ecommerceusb.model.Cart;
import co.edu.usbcali.ecommerceusb.model.User;
import co.edu.usbcali.ecommerceusb.repository.CartRepository;
import co.edu.usbcali.ecommerceusb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public CartResponse getCartById(Integer id) throws Exception {
        if (id == null || id <= 0) {
            throw new Exception("Debe ingresar el id para buscar");
        }
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new Exception(
                        String.format("Carrito no encontrado con el id: %d", id)));
        return CartMapper.modelToCartResponse(cart);
    }

    @Override
    public CartResponse createCart(CreateCartRequest request) throws Exception {
        if (Objects.isNull(request)) {
            throw new Exception("El objeto CreateCartRequest no puede ser nulo.");
        }
        if (request.getUserId() == null || request.getUserId() <= 0) {
            throw new Exception("El campo userId debe ser mayor a 0.");
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        Cart cart = CartMapper.createCartRequestToCart(request, user);
        cart = cartRepository.save(cart);
        return CartMapper.modelToCartResponse(cart);
    }
}
