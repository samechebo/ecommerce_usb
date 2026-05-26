package co.edu.usbcali.ecommerceusb.Service.impl;

import co.edu.usbcali.ecommerceusb.Service.InventoryService;
import co.edu.usbcali.ecommerceusb.dto.CreateInventoryRequest;
import co.edu.usbcali.ecommerceusb.dto.InventoryResponse;
import co.edu.usbcali.ecommerceusb.exception.BadRequestException;
import co.edu.usbcali.ecommerceusb.exception.InternalServerErrorException;
import co.edu.usbcali.ecommerceusb.exception.NotFoundException;
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
    public InventoryResponse getInventoryById(Integer id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Debe ingresar el id para buscar");
        }
        return inventoryRepository.findById(id)
                .map(InventoryMapper::modelToInventoryResponse)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Inventario no encontrado con el id: %d", id)));
    }

    @Override
    public InventoryResponse createInventory(CreateInventoryRequest request) {
        if (Objects.isNull(request)) {
            throw new BadRequestException("El objeto CreateInventoryRequest no puede ser nulo.");
        }
        if (request.getProductId() == null || request.getProductId() <= 0) {
            throw new BadRequestException("El campo productId debe ser mayor a 0.");
        }
        if (request.getStock() == null || request.getStock() < 0) {
            throw new BadRequestException("El campo stock no puede ser negativo.");
        }
        if (inventoryRepository.existsByProductId(request.getProductId())) {
            throw new InternalServerErrorException("Ya existe un inventario para ese producto.");
        }
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));
        try {
            Inventory inventory = InventoryMapper.createInventoryRequestToInventory(request, product);
            inventory = inventoryRepository.save(inventory);
            return InventoryMapper.modelToInventoryResponse(inventory);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error al guardar el inventario: " + e.getMessage());
        }
    }

    @Override
    public InventoryResponse updateInventory(Integer id, CreateInventoryRequest request) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Debe ingresar el id para actualizar");
        }
        if (Objects.isNull(request)) {
            throw new BadRequestException("El objeto CreateInventoryRequest no puede ser nulo.");
        }
        if (request.getStock() == null || request.getStock() < 0) {
            throw new BadRequestException("El campo stock no puede ser negativo.");
        }
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Inventario no encontrado con el id: %d", id)));
        try {
            inventory.setStock(request.getStock());
            inventory.setUpdatedAt(OffsetDateTime.now());
            inventory = inventoryRepository.save(inventory);
            return InventoryMapper.modelToInventoryResponse(inventory);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error al actualizar el inventario: " + e.getMessage());
        }
    }

    @Override
    public void deleteInventory(Integer id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Debe ingresar el id para eliminar");
        }
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Inventario no encontrado con el id: %d", id)));
        try {
            inventoryRepository.delete(inventory);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error al eliminar el inventario: " + e.getMessage());
        }
    }
}