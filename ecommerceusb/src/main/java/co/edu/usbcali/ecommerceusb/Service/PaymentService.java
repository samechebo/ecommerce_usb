package co.edu.usbcali.ecommerceusb.Service;

import co.edu.usbcali.ecommerceusb.dto.CreatePaymentRequest;
import co.edu.usbcali.ecommerceusb.dto.PaymentResponse;
import co.edu.usbcali.ecommerceusb.dto.UpdatePaymentRequest;

import java.util.List;

public interface PaymentService {
    List<PaymentResponse> getPayments();
    PaymentResponse getPaymentById(Integer id);
    PaymentResponse createPayment(CreatePaymentRequest request);
    PaymentResponse updatePayment(Integer id, UpdatePaymentRequest request);
    void deletePayment(Integer id);
}