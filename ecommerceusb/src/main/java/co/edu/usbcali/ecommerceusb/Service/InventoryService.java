package co.edu.usbcali.ecommerceusb.Service;

import co.edu.usbcali.ecommerceusb.dto.CreateInventoryRequest;
import co.edu.usbcali.ecommerceusb.dto.InventoryResponse;

import java.util.List;

public interface InventoryService {
    List<InventoryResponse> getInventories();
    InventoryResponse getInventoryById(Integer id) throws Exception;
    InventoryResponse createInventory(CreateInventoryRequest request) throws Exception;
    InventoryResponse updateInventory(Integer id, CreateInventoryRequest request) throws Exception;
    void deleteInventory(Integer id) throws Exception;
}