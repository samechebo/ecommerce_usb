package co.edu.usbcali.ecommerceusb.controller;

import co.edu.usbcali.ecommerceusb.Service.InventoryService;
import co.edu.usbcali.ecommerceusb.dto.CreateInventoryRequest;
import co.edu.usbcali.ecommerceusb.dto.InventoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/all")
    public List<InventoryResponse> getAll() {
        return inventoryService.getInventories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryResponse> getById(@PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(inventoryService.getInventoryById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<InventoryResponse> create(@RequestBody CreateInventoryRequest request) throws Exception {
        return new ResponseEntity<>(inventoryService.createInventory(request), HttpStatus.CREATED);
    }
}