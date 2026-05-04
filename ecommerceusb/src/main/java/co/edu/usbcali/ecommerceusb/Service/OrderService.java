package co.edu.usbcali.ecommerceusb.Service;


import co.edu.usbcali.ecommerceusb.dto.CreateOrderRequest;
import co.edu.usbcali.ecommerceusb.dto.OrderResponse;

import java.util.List;

public interface OrderService {
    List<OrderResponse> getOrders();
    OrderResponse getOrderById(Integer id) throws Exception;
    OrderResponse createOrder(CreateOrderRequest request) throws Exception;
}
