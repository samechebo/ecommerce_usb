package co.edu.usbcali.ecommerceusb.Service.impl;

import co.edu.usbcali.ecommerceusb.Service.PaymentService;
import co.edu.usbcali.ecommerceusb.dto.CreatePaymentRequest;
import co.edu.usbcali.ecommerceusb.dto.PaymentResponse;
import co.edu.usbcali.ecommerceusb.dto.UpdatePaymentRequest;
import co.edu.usbcali.ecommerceusb.mapper.PaymentMapper;
import co.edu.usbcali.ecommerceusb.model.Order;
import co.edu.usbcali.ecommerceusb.model.Payment;
import co.edu.usbcali.ecommerceusb.repository.OrderRepository;
import co.edu.usbcali.ecommerceusb.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<PaymentResponse> getPayments() {
        List<Payment> list = paymentRepository.findAll();
        if (list.isEmpty()) return List.of();
        return PaymentMapper.modelToPaymentResponseList(list);
    }

    @Override
    public PaymentResponse getPaymentById(Integer id) throws Exception {
        if (id == null || id <= 0) {
            throw new Exception("Debe ingresar el id para buscar");
        }
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new Exception(
                        String.format("Pago no encontrado con el id: %d", id)));
        return PaymentMapper.modelToPaymentResponse(payment);
    }

    @Override
    public PaymentResponse createPayment(CreatePaymentRequest request) throws Exception {
        if (Objects.isNull(request)) {
            throw new Exception("El objeto CreatePaymentRequest no puede ser nulo.");
        }
        if (request.getOrderId() == null || request.getOrderId() <= 0) {
            throw new Exception("El campo orderId debe ser mayor a 0.");
        }
        if (Objects.isNull(request.getIdempotencyKey()) || request.getIdempotencyKey().isBlank()) {
            throw new Exception("El campo idempotencyKey no puede ser nulo.");
        }
        if (paymentRepository.existsByIdempotencyKey(request.getIdempotencyKey())) {
            throw new Exception("Ya existe un pago con ese idempotencyKey.");
        }
        if (paymentRepository.existsByOrderId(request.getOrderId())) {
            throw new Exception("Ya existe un pago para esta orden.");
        }

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new Exception("Orden no encontrada"));

        Payment payment = PaymentMapper.createPaymentRequestToPayment(request, order);
        payment = paymentRepository.save(payment);
        return PaymentMapper.modelToPaymentResponse(payment);
    }
    @Override
    public PaymentResponse updatePayment(Integer id, UpdatePaymentRequest request) throws Exception {
        if (id == null || id <= 0) {
            throw new Exception("Debe ingresar el id para actualizar");
        }
        if (Objects.isNull(request)) {
            throw new Exception("El objeto UpdatePaymentRequest no puede ser nulo.");
        }
        if (Objects.isNull(request.getStatus()) || request.getStatus().isBlank()) {
            throw new Exception("El campo status no puede ser nulo.");
        }

        Payment.PaymentStatus paymentStatus;
        try {
            paymentStatus = Payment.PaymentStatus.valueOf(request.getStatus());
        } catch (IllegalArgumentException e) {
            throw new Exception("El status debe ser: SUCCEEDED o FAILED.");
        }

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new Exception(
                        String.format("Pago no encontrado con el id: %d", id)));

        payment.setStatus(paymentStatus);

        payment = paymentRepository.save(payment);
        return PaymentMapper.modelToPaymentResponse(payment);
    }
}
