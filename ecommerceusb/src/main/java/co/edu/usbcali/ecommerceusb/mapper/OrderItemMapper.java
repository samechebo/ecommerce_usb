package co.edu.usbcali.ecommerceusb.mapper;

import co.edu.usbcali.ecommerceusb.dto.CreateOrderItemRequest;
import co.edu.usbcali.ecommerceusb.dto.OrderItemResponse;
import co.edu.usbcali.ecommerceusb.model.Order;
import co.edu.usbcali.ecommerceusb.model.OrderItem;
import co.edu.usbcali.ecommerceusb.model.Product;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public class OrderItemMapper {

    public static OrderItemResponse modelToOrderItemResponse(OrderItem item) {
        return OrderItemResponse.builder()
                .id(item.getId())
                .orderId(item.getOrder() != null ? item.getOrder().getId() : null)
                .productId(item.getProduct() != null ? item.getProduct().getId() : null)
                .productName(item.getProduct() != null ? item.getProduct().getName() : null)
                .quantity(item.getQuantity())
                .unitPriceSnapshot(item.getUnitPriceSnapshot())
                .lineTotal(item.getLineTotal())
                .build();
    }

    public static List<OrderItemResponse> modelToOrderItemResponseList(List<OrderItem> list) {
        return list.stream().map(OrderItemMapper::modelToOrderItemResponse).toList();
    }

    public static OrderItem createOrderItemRequestToOrderItem(
            CreateOrderItemRequest request, Order order, Product product) {

        BigDecimal unitPrice = request.getUnitPriceSnapshot() != null
                ? request.getUnitPriceSnapshot()
                : product.getPrice();
        BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(request.getQuantity()));

        return OrderItem.builder()
                .order(order)
                .product(product)
                .quantity(request.getQuantity())
                .unitPriceSnapshot(unitPrice)
                .lineTotal(lineTotal)
                .createdAt(OffsetDateTime.now())
                .build();
    }
}