package co.edu.usbcali.ecommerceusb.Service.impl;

import co.edu.usbcali.ecommerceusb.Service.InventoryService;
import co.edu.usbcali.ecommerceusb.dto.CreateInventoryRequest;
import co.edu.usbcali.ecommerceusb.dto.InventoryResponse;
import co.edu.usbcali.ecommerceusb.mapper.InventoryMapper;
import co.edu.usbcali.ecommerceusb.model.Inventory;
import co.edu.usbcali.ecommerceusb.model.Product;
import co.edu.usbcali.ecommerceusb.repository.InventoryRepository;
import co.edu.usbcali.ecommerceusb.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<InventoryResponse> getInventories() {
        List<Inventory> list = inventoryRepository.findAll();
        if (list.isEmpty()) return List.of();
        return InventoryMapper.modelToInventoryResponseList(list);
    }

    @Override
    public InventoryResponse getInventoryById(Integer id) throws Exception {
        if (id == null || id <= 0) {
            throw new Exception("Debe ingresar el id para buscar");
        }
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new Exception(
                        String.format("Inventario no encontrado con el id: %d", id)));
        return InventoryMapper.modelToInventoryResponse(inventory);
    }

    @Override
    public InventoryResponse createInventory(CreateInventoryRequest request) throws Exception {
        if (Objects.isNull(request)) {
            throw new Exception("El objeto CreateInventoryRequest no puede ser nulo.");
        }
        if (request.getProductId() == null || request.getProductId() <= 0) {
            throw new Exception("El campo productId debe ser mayor a 0.");
        }
        if (request.getStock() == null || request.getStock() < 0) {
            throw new Exception("El campo stock no puede ser negativo.");
        }
        if (inventoryRepository.existsByProductId(request.getProductId())) {
            throw new Exception("Ya existe un inventario para ese producto.");
        }

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new Exception("Producto no encontrado"));

        Inventory inventory = InventoryMapper.createInventoryRequestToInventory(request, product);
        inventory = inventoryRepository.save(inventory);
        return InventoryMapper.modelToInventoryResponse(inventory);
    }
    @Override
    public InventoryResponse updateInventory(Integer id, CreateInventoryRequest request) throws Exception {
        if (id == null || id <= 0) {
            throw new Exception("Debe ingresar el id para actualizar");
        }
        if (Objects.isNull(request)) {
            throw new Exception("El objeto CreateInventoryRequest no puede ser nulo.");
        }
        if (request.getStock() == null || request.getStock() < 0) {
            throw new Exception("El campo stock no puede ser negativo.");
        }

        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new Exception(
                        String.format("Inventario no encontrado con el id: %d", id)));

        inventory.setStock(request.getStock());
        inventory.setUpdatedAt(OffsetDateTime.now());

        inventory = inventoryRepository.save(inventory);
        return InventoryMapper.modelToInventoryResponse(inventory);
    }
}