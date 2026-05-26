package co.edu.usbcali.ecommerceusb.Service;

import co.edu.usbcali.ecommerceusb.dto.CreateOrderItemRequest;
import co.edu.usbcali.ecommerceusb.dto.OrderItemResponse;
import co.edu.usbcali.ecommerceusb.dto.UpdateOrderItemRequest;

import java.util.List;

public interface OrderItemService {
    List<OrderItemResponse> getOrderItems();
    OrderItemResponse getOrderItemById(Integer id);
    OrderItemResponse createOrderItem(CreateOrderItemRequest request);
    OrderItemResponse updateOrderItem(Integer id, UpdateOrderItemRequest request);
    void deleteOrderItem(Integer id);
}
