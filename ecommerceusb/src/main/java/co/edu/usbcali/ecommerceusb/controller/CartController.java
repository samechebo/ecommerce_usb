package co.edu.usbcali.ecommerceusb.controller;

import co.edu.usbcali.ecommerceusb.Service.CartService;
import co.edu.usbcali.ecommerceusb.dto.CartResponse;
import co.edu.usbcali.ecommerceusb.dto.CreateCartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/all")
    public List<CartResponse> getAll() {
        return cartService.getCarts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartResponse> getById(@PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(cartService.getCartById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CartResponse> create(@RequestBody CreateCartRequest request) throws Exception {
        return new ResponseEntity<>(cartService.createCart(request), HttpStatus.CREATED);
    }
}