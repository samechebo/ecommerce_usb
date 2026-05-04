package co.edu.usbcali.ecommerceusb.Service;

import co.edu.usbcali.ecommerceusb.dto.CreateOrderItemRequest;
import co.edu.usbcali.ecommerceusb.dto.OrderItemResponse;

import java.util.List;

public interface OrderItemService {
    List<OrderItemResponse> getOrderItems();
    OrderItemResponse getOrderItemById(Integer id) throws Exception;
    OrderItemResponse createOrderItem(CreateOrderItemRequest request) throws Exception;
}