package co.edu.usbcali.ecommerceusb.controller;

import co.edu.usbcali.ecommerceusb.Service.InventoryMovementService;
import co.edu.usbcali.ecommerceusb.dto.CreateInventoryMovementRequest;
import co.edu.usbcali.ecommerceusb.dto.InventoryMovementResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory-movement")
public class InventoryMovementController {

    @Autowired
    private InventoryMovementService inventoryMovementService;

    @GetMapping("/all")
    public List<InventoryMovementResponse> getAll() {
        return inventoryMovementService.getInventoryMovements();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryMovementResponse> getById(@PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(inventoryMovementService.getInventoryMovementById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<InventoryMovementResponse> create(
            @RequestBody CreateInventoryMovementRequest request) throws Exception {
        return new ResponseEntity<>(
                inventoryMovementService.createInventoryMovement(request), HttpStatus.CREATED);
    }
}
