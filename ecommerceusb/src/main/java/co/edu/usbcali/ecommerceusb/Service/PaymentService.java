package co.edu.usbcali.ecommerceusb.Service;

import co.edu.usbcali.ecommerceusb.dto.CreatePaymentRequest;
import co.edu.usbcali.ecommerceusb.dto.PaymentResponse;
import co.edu.usbcali.ecommerceusb.dto.UpdatePaymentRequest;

import java.util.List;

public interface PaymentService {
    List<PaymentResponse> getPayments();
    PaymentResponse getPaymentById(Integer id) throws Exception;
    PaymentResponse createPayment(CreatePaymentRequest request) throws Exception;
    PaymentResponse updatePayment(Integer id, UpdatePaymentRequest request) throws Exception;
}
