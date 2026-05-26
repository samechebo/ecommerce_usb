package co.edu.usbcali.ecommerceusb.Service;

import co.edu.usbcali.ecommerceusb.dto.CreateInventoryMovementRequest;
import co.edu.usbcali.ecommerceusb.dto.InventoryMovementResponse;
import co.edu.usbcali.ecommerceusb.dto.UpdateInventoryMovementRequest;

import java.util.List;

public interface InventoryMovementService {
    List<InventoryMovementResponse> getInventoryMovements();
    InventoryMovementResponse getInventoryMovementById(Integer id);
    InventoryMovementResponse createInventoryMovement(CreateInventoryMovementRequest request);
    InventoryMovementResponse updateInventoryMovement(Integer id, UpdateInventoryMovementRequest request);
    void deleteInventoryMovement(Integer id);
}