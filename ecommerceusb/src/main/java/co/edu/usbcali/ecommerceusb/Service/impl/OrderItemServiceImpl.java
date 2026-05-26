package co.edu.usbcali.ecommerceusb.Service.impl;

import co.edu.usbcali.ecommerceusb.Service.OrderItemService;
import co.edu.usbcali.ecommerceusb.dto.CreateOrderItemRequest;
import co.edu.usbcali.ecommerceusb.dto.OrderItemResponse;
import co.edu.usbcali.ecommerceusb.dto.UpdateOrderItemRequest;
import co.edu.usbcali.ecommerceusb.exception.BadRequestException;
import co.edu.usbcali.ecommerceusb.exception.InternalServerErrorException;
import co.edu.usbcali.ecommerceusb.exception.NotFoundException;
import co.edu.usbcali.ecommerceusb.mapper.OrderItemMapper;
import co.edu.usbcali.ecommerceusb.model.Order;
import co.edu.usbcali.ecommerceusb.model.OrderItem;
import co.edu.usbcali.ecommerceusb.model.Product;
import co.edu.usbcali.ecommerceusb.repository.OrderItemRepository;
import co.edu.usbcali.ecommerceusb.repository.OrderRepository;
import co.edu.usbcali.ecommerceusb.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<OrderItemResponse> getOrderItems() {
        List<OrderItem> list = orderItemRepository.findAll();
        if (list.isEmpty()) return List.of();
        return OrderItemMapper.modelToOrderItemResponseList(list);
    }

    @Override
    public OrderItemResponse getOrderItemById(Integer id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Debe ingresar el id para buscar");
        }
        return orderItemRepository.findById(id)
                .map(OrderItemMapper::modelToOrderItemResponse)
                .orElseThrow(() -> new NotFoundException(
                        String.format("OrderItem no encontrado con el id: %d", id)));
    }

    @Override
    public OrderItemResponse createOrderItem(CreateOrderItemRequest request) {
        if (Objects.isNull(request)) {
            throw new BadRequestException("El objeto CreateOrderItemRequest no puede ser nulo.");
        }
        if (request.getOrderId() == null || request.getOrderId() <= 0) {
            throw new BadRequestException("El campo orderId debe ser mayor a 0.");
        }
        if (request.getProductId() == null || request.getProductId() <= 0) {
            throw new BadRequestException("El campo productId debe ser mayor a 0.");
        }
        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new BadRequestException("El campo quantity debe ser mayor a 0.");
        }
        if (orderItemRepository.existsByOrderIdAndProductId(request.getOrderId(), request.getProductId())) {
            throw new InternalServerErrorException("El producto ya existe en esta orden.");
        }
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new NotFoundException("Orden no encontrada"));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));
        try {
            OrderItem item = OrderItemMapper.createOrderItemRequestToOrderItem(request, order, product);
            item = orderItemRepository.save(item);
            return OrderItemMapper.modelToOrderItemResponse(item);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error al guardar el item de la orden: " + e.getMessage());
        }
    }

    @Override
    public OrderItemResponse updateOrderItem(Integer id, UpdateOrderItemRequest request) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Debe ingresar el id para actualizar");
        }
        if (Objects.isNull(request)) {
            throw new BadRequestException("El objeto UpdateOrderItemRequest no puede ser nulo.");
        }
        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new BadRequestException("El campo quantity debe ser mayor a 0.");
        }
        OrderItem item = orderItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("OrderItem no encontrado con el id: %d", id)));
        try {
            BigDecimal newLineTotal = item.getUnitPriceSnapshot()
                    .multiply(BigDecimal.valueOf(request.getQuantity()));
            item.setQuantity(request.getQuantity());
            item.setLineTotal(newLineTotal);
            item = orderItemRepository.save(item);
            return OrderItemMapper.modelToOrderItemResponse(item);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error al actualizar el item de la orden: " + e.getMessage());
        }
    }

    @Override
    public void deleteOrderItem(Integer id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Debe ingresar el id para eliminar");
        }
        OrderItem item = orderItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("OrderItem no encontrado con el id: %d", id)));
        try {
            orderItemRepository.delete(item);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error al eliminar el item de la orden: " + e.getMessage());
        }
    }
}
