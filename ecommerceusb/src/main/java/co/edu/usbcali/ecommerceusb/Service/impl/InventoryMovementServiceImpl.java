package co.edu.usbcali.ecommerceusb.Service.impl;

import co.edu.usbcali.ecommerceusb.Service.InventoryMovementService;
import co.edu.usbcali.ecommerceusb.dto.CreateInventoryMovementRequest;
import co.edu.usbcali.ecommerceusb.dto.InventoryMovementResponse;
import co.edu.usbcali.ecommerceusb.dto.UpdateInventoryMovementRequest;
import co.edu.usbcali.ecommerceusb.exception.BadRequestException;
import co.edu.usbcali.ecommerceusb.exception.InternalServerErrorException;
import co.edu.usbcali.ecommerceusb.exception.NotFoundException;
import co.edu.usbcali.ecommerceusb.mapper.InventoryMovementMapper;
import co.edu.usbcali.ecommerceusb.model.InventoryMovement;
import co.edu.usbcali.ecommerceusb.model.Order;
import co.edu.usbcali.ecommerceusb.model.Product;
import co.edu.usbcali.ecommerceusb.repository.InventoryMovementRepository;
import co.edu.usbcali.ecommerceusb.repository.OrderRepository;
import co.edu.usbcali.ecommerceusb.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class InventoryMovementServiceImpl implements InventoryMovementService {

    @Autowired
    private InventoryMovementRepository inventoryMovementRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;

    private static final List<String> VALID_TYPES =
            Arrays.stream(InventoryMovement.MovementType.values())
                    .map(Enum::name).toList();

    @Override
    public List<InventoryMovementResponse> getInventoryMovements() {
        List<InventoryMovement> list = inventoryMovementRepository.findAll();
        if (list.isEmpty()) return List.of();
        return InventoryMovementMapper.modelToResponseList(list);
    }

    @Override
    public InventoryMovementResponse getInventoryMovementById(Integer id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Debe ingresar el id para buscar");
        }
        return inventoryMovementRepository.findById(id)
                .map(InventoryMovementMapper::modelToResponse)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Movimiento de inventario no encontrado con el id: %d", id)));
    }

    @Override
    public InventoryMovementResponse createInventoryMovement(CreateInventoryMovementRequest request) {
        if (Objects.isNull(request)) {
            throw new BadRequestException("El objeto CreateInventoryMovementRequest no puede ser nulo.");
        }
        if (request.getProductId() == null || request.getProductId() <= 0) {
            throw new BadRequestException("El campo productId debe ser mayor a 0.");
        }
        if (Objects.isNull(request.getType()) || request.getType().isBlank()) {
            throw new BadRequestException("El campo type no puede ser nulo.");
        }
        if (!VALID_TYPES.contains(request.getType())) {
            throw new BadRequestException("El campo type debe ser uno de: " + VALID_TYPES);
        }
        if (request.getQty() == null || request.getQty() <= 0) {
            throw new BadRequestException("El campo qty debe ser mayor a 0.");
        }
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));
        Order order = null;
        if (request.getOrderId() != null) {
            if (request.getOrderId() <= 0) {
                throw new BadRequestException("El campo orderId debe ser mayor a 0.");
            }
            order = orderRepository.findById(request.getOrderId())
                    .orElseThrow(() -> new NotFoundException("Orden no encontrada"));
        }
        try {
            InventoryMovement im = InventoryMovementMapper.createRequestToModel(request, product, order);
            im = inventoryMovementRepository.save(im);
            return InventoryMovementMapper.modelToResponse(im);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error al guardar el movimiento de inventario: " + e.getMessage());
        }
    }

    @Override
    public InventoryMovementResponse updateInventoryMovement(Integer id, UpdateInventoryMovementRequest request) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Debe ingresar el id para actualizar");
        }
        if (Objects.isNull(request)) {
            throw new BadRequestException("El objeto UpdateInventoryMovementRequest no puede ser nulo.");
        }
        if (request.getQty() == null || request.getQty() <= 0) {
            throw new BadRequestException("El campo qty debe ser mayor a 0.");
        }
        InventoryMovement im = inventoryMovementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Movimiento de inventario no encontrado con el id: %d", id)));
        try {
            im.setQty(request.getQty());
            im = inventoryMovementRepository.save(im);
            return InventoryMovementMapper.modelToResponse(im);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error al actualizar el movimiento de inventario: " + e.getMessage());
        }
    }

    @Override
    public void deleteInventoryMovement(Integer id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Debe ingresar el id para eliminar");
        }
        InventoryMovement im = inventoryMovementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Movimiento de inventario no encontrado con el id: %d", id)));
        try {
            inventoryMovementRepository.delete(im);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error al eliminar el movimiento de inventario: " + e.getMessage());
        }
    }
}