package co.edu.usbcali.ecommerceusb.Service;

import co.edu.usbcali.ecommerceusb.dto.CreateInventoryMovementRequest;
import co.edu.usbcali.ecommerceusb.dto.InventoryMovementResponse;

import java.util.List;

public interface InventoryMovementService {
    List<InventoryMovementResponse> getInventoryMovements();
    InventoryMovementResponse getInventoryMovementById(Integer id) throws Exception;
    InventoryMovementResponse createInventoryMovement(CreateInventoryMovementRequest request) throws Exception;
}