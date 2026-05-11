package co.edu.usbcali.ecommerceusb.Service;

import co.edu.usbcali.ecommerceusb.dto.CreateInventoryMovementRequest;
import co.edu.usbcali.ecommerceusb.dto.InventoryMovementResponse;
import co.edu.usbcali.ecommerceusb.dto.UpdateInventoryMovementRequest;

import java.util.List;

public interface InventoryMovementService {
    List<InventoryMovementResponse> getInventoryMovements();
    InventoryMovementResponse getInventoryMovementById(Integer id) throws Exception;
    InventoryMovementResponse createInventoryMovement(CreateInventoryMovementRequest request) throws Exception;
    InventoryMovementResponse updateInventoryMovement(
            Integer id, UpdateInventoryMovementRequest request) throws Exception;

}