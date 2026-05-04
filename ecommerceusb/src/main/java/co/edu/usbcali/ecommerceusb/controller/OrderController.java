package co.edu.usbcali.ecommerceusb.controller;

import co.edu.usbcali.ecommerceusb.Service.OrderService;
import co.edu.usbcali.ecommerceusb.dto.CreateOrderRequest;
import co.edu.usbcali.ecommerceusb.dto.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/all")
    public List<OrderResponse> getAll() {
        return orderService.getOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getById(@PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(orderService.getOrderById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody CreateOrderRequest request) throws Exception {
        return new ResponseEntity<>(orderService.createOrder(request), HttpStatus.CREATED);
    }
}