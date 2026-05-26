package co.edu.usbcali.ecommerceusb.Service.impl;

import co.edu.usbcali.ecommerceusb.Service.OrderService;
import co.edu.usbcali.ecommerceusb.dto.CreateOrderRequest;
import co.edu.usbcali.ecommerceusb.dto.OrderResponse;
import co.edu.usbcali.ecommerceusb.dto.UpdateOrderRequest;
import co.edu.usbcali.ecommerceusb.exception.BadRequestException;
import co.edu.usbcali.ecommerceusb.exception.InternalServerErrorException;
import co.edu.usbcali.ecommerceusb.exception.NotFoundException;
import co.edu.usbcali.ecommerceusb.mapper.OrderMapper;
import co.edu.usbcali.ecommerceusb.model.Order;
import co.edu.usbcali.ecommerceusb.model.User;
import co.edu.usbcali.ecommerceusb.repository.OrderRepository;
import co.edu.usbcali.ecommerceusb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
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
    public OrderResponse getOrderById(Integer id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Debe ingresar el id para buscar");
        }
        return orderRepository.findById(id)
                .map(OrderMapper::modelToOrderResponse)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Orden no encontrada con el id: %d", id)));
    }

    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {
        if (Objects.isNull(request)) {
            throw new BadRequestException("El objeto CreateOrderRequest no puede ser nulo.");
        }
        if (request.getUserId() == null || request.getUserId() <= 0) {
            throw new BadRequestException("El campo userId debe ser mayor a 0.");
        }
        if (Objects.isNull(request.getTotalAmount()) || request.getTotalAmount().doubleValue() < 0) {
            throw new BadRequestException("El campo totalAmount no puede ser negativo.");
        }
        if (Objects.isNull(request.getCurrency()) || request.getCurrency().isBlank()) {
            throw new BadRequestException("El campo currency no puede ser nulo.");
        }
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        try {
            Order order = OrderMapper.createOrderRequestToOrder(request, user);
            order = orderRepository.save(order);
            return OrderMapper.modelToOrderResponse(order);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error al guardar la orden: " + e.getMessage());
        }
    }

    @Override
    public OrderResponse updateOrder(Integer id, UpdateOrderRequest request) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Debe ingresar el id para actualizar");
        }
        if (Objects.isNull(request)) {
            throw new BadRequestException("El objeto UpdateOrderRequest no puede ser nulo.");
        }
        if (Objects.isNull(request.getStatus()) || request.getStatus().isBlank()) {
            throw new BadRequestException("El campo status no puede ser nulo.");
        }
        Order.OrderStatus orderStatus;
        try {
            orderStatus = Order.OrderStatus.valueOf(request.getStatus());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("El status debe ser: CREATED, PAID o CANCELLED.");
        }
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Orden no encontrada con el id: %d", id)));
        try {
            order.setStatus(orderStatus);
            if (orderStatus == Order.OrderStatus.PAID) {
                order.setPaidAt(OffsetDateTime.now());
            } else if (orderStatus == Order.OrderStatus.CANCELLED) {
                order.setCancelledAt(OffsetDateTime.now());
            }
            order = orderRepository.save(order);
            return OrderMapper.modelToOrderResponse(order);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error al actualizar la orden: " + e.getMessage());
        }
    }

    @Override
    public void deleteOrder(Integer id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Debe ingresar el id para eliminar");
        }
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Orden no encontrada con el id: %d", id)));
        try {
            orderRepository.delete(order);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error al eliminar la orden: " + e.getMessage());
        }
    }
}