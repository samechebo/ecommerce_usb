package co.edu.usbcali.ecommerceusb.mapper;

import co.edu.usbcali.ecommerceusb.dto.CreateOrderRequest;
import co.edu.usbcali.ecommerceusb.dto.OrderResponse;
import co.edu.usbcali.ecommerceusb.model.Order;
import co.edu.usbcali.ecommerceusb.model.User;

import java.time.OffsetDateTime;
import java.util.List;

public class OrderMapper {

    public static OrderResponse modelToOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUser() != null ? order.getUser().getId() : null)
                .userFullName(order.getUser() != null ? order.getUser().getFullName() : null)
                .status(order.getStatus() != null ? order.getStatus().name() : null)
                .totalAmount(order.getTotalAmount())
                .currency(order.getCurrency())
                .build();
    }

    public static List<OrderResponse> modelToOrderResponseList(List<Order> list) {
        return list.stream().map(OrderMapper::modelToOrderResponse).toList();
    }

    public static Order createOrderRequestToOrder(CreateOrderRequest request, User user) {
        return Order.builder()
                .user(user)
                .status(Order.OrderStatus.CREATED)
                .totalAmount(request.getTotalAmount())
                .currency(request.getCurrency())
                .createdAt(OffsetDateTime.now())
                .build();
    }
}