package co.edu.usbcali.ecommerceusb.controller;

import co.edu.usbcali.ecommerceusb.Service.OrderItemService;
import co.edu.usbcali.ecommerceusb.dto.CreateOrderItemRequest;
import co.edu.usbcali.ecommerceusb.dto.OrderItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-item")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping("/all")
    public List<OrderItemResponse> getAll() {
        return orderItemService.getOrderItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemResponse> getById(@PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(orderItemService.getOrderItemById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderItemResponse> create(@RequestBody CreateOrderItemRequest request) throws Exception {
        return new ResponseEntity<>(orderItemService.createOrderItem(request), HttpStatus.CREATED);
    }
}
