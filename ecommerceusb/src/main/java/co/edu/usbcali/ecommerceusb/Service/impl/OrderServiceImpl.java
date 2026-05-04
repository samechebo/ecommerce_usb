package co.edu.usbcali.ecommerceusb.Service.impl;

import co.edu.usbcali.ecommerceusb.Service.OrderService;
import co.edu.usbcali.ecommerceusb.dto.CreateOrderRequest;
import co.edu.usbcali.ecommerceusb.dto.OrderResponse;
import co.edu.usbcali.ecommerceusb.mapper.OrderMapper;
import co.edu.usbcali.ecommerceusb.model.Order;
import co.edu.usbcali.ecommerceusb.model.User;
import co.edu.usbcali.ecommerceusb.repository.OrderRepository;
import co.edu.usbcali.ecommerceusb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<OrderResponse> getOrders() {
        List<Order> list = orderRepository.findAll();
        if (list.isEmpty()) return List.of();
        return OrderMapper.modelToOrderResponseList(list);
    }

    @Override
    public OrderResponse getOrderById(Integer id) throws Exception {
        if (id == null || id <= 0) {
            throw new Exception("Debe ingresar el id para buscar");
        }
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new Exception(
                        String.format("Orden no encontrada con el id: %d", id)));
        return OrderMapper.modelToOrderResponse(order);
    }

    @Override
    public OrderResponse createOrder(CreateOrderRequest request) throws Exception {
        if (Objects.isNull(request)) {
            throw new Exception("El objeto CreateOrderRequest no puede ser nulo.");
        }
        if (request.getUserId() == null || request.getUserId() <= 0) {
            throw new Exception("El campo userId debe ser mayor a 0.");
        }
        if (Objects.isNull(request.getTotalAmount()) || request.getTotalAmount().doubleValue() < 0) {
            throw new Exception("El campo totalAmount no puede ser negativo.");
        }
        if (Objects.isNull(request.getCurrency()) || request.getCurrency().isBlank()) {
            throw new Exception("El campo currency no puede ser nulo.");
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        Order order = OrderMapper.createOrderRequestToOrder(request, user);
        order = orderRepository.save(order);
        return OrderMapper.modelToOrderResponse(order);
    }
}