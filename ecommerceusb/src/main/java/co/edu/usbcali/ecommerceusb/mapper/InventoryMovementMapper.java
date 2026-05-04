package co.edu.usbcali.ecommerceusb.mapper;

import co.edu.usbcali.ecommerceusb.dto.CreateInventoryMovementRequest;
import co.edu.usbcali.ecommerceusb.dto.InventoryMovementResponse;
import co.edu.usbcali.ecommerceusb.model.InventoryMovement;
import co.edu.usbcali.ecommerceusb.model.Order;
import co.edu.usbcali.ecommerceusb.model.Product;

import java.time.OffsetDateTime;
import java.util.List;

public class InventoryMovementMapper {

    public static InventoryMovementResponse modelToResponse(InventoryMovement im) {
        return InventoryMovementResponse.builder()
                .id(im.getId())
                .productId(im.getProduct() != null ? im.getProduct().getId() : null)
                .productName(im.getProduct() != null ? im.getProduct().getName() : null)
                .orderId(im.getOrder() != null ? im.getOrder().getId() : null)
                .type(im.getType() != null ? im.getType().name() : null)
                .qty(im.getQty())
                .build();
    }

    public static List<InventoryMovementResponse> modelToResponseList(List<InventoryMovement> list) {
        return list.stream().map(InventoryMovementMapper::modelToResponse).toList();
    }

    public static InventoryMovement createRequestToModel(
            CreateInventoryMovementRequest request, Product product, Order order) {
        return InventoryMovement.builder()
                .product(product)
                .order(order)
                .type(InventoryMovement.MovementType.valueOf(request.getType()))
                .qty(request.getQty())
                .createdAt(OffsetDateTime.now())
                .build();
    }
}
