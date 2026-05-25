package co.edu.usbcali.ecommerceusb.controller;

import co.edu.usbcali.ecommerceusb.Service.CartItemService;
import co.edu.usbcali.ecommerceusb.dto.CartItemResponse;
import co.edu.usbcali.ecommerceusb.dto.CreateCartItemRequest;
import co.edu.usbcali.ecommerceusb.dto.UpdateCartItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart-item")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @GetMapping("/all")
    public List<CartItemResponse> getAll() {
        return cartItemService.getCartItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartItemResponse> getById(@PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(cartItemService.getCartItemById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CartItemResponse> create(@RequestBody CreateCartItemRequest request) throws Exception {
        return new ResponseEntity<>(cartItemService.createCartItem(request), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CartItemResponse> updateCartItem(
            @PathVariable Integer id,
            @RequestBody UpdateCartItemRequest request) throws Exception {
        return new ResponseEntity<>(cartItemService.updateCartItem(id, request), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) throws Exception {
        cartItemService.deleteCartItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
