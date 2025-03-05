package edu.neu.csye6200.parkingapp.service.interfaces;

import com.stripe.exception.StripeException;
import edu.neu.csye6200.parkingapp.dto.PaymentDTO;

import java.util.Optional;

public interface IPaymentService {
    String createPayment(PaymentDTO paymentDTO) throws StripeException;

    Optional<PaymentDTO> getPaymentById(Long id);
}
