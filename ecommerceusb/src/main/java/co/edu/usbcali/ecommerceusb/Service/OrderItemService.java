package co.edu.usbcali.ecommerceusb.Service;

import co.edu.usbcali.ecommerceusb.dto.CreateOrderItemRequest;
import co.edu.usbcali.ecommerceusb.dto.OrderItemResponse;
import co.edu.usbcali.ecommerceusb.dto.UpdateOrderItemRequest;

import java.util.List;

public interface OrderItemService {
    List<OrderItemResponse> getOrderItems();
    OrderItemResponse getOrderItemById(Integer id) throws Exception;
    OrderItemResponse createOrderItem(CreateOrderItemRequest request) throws Exception;
    OrderItemResponse updateOrderItem(Integer id, UpdateOrderItemRequest request) throws Exception;
    void deleteOrderItem(Integer id) throws Exception;
}