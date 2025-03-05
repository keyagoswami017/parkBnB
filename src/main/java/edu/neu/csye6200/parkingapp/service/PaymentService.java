package edu.neu.csye6200.parkingapp.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.param.PaymentMethodAttachParams;
import edu.neu.csye6200.parkingapp.config.StripeConfig;
import edu.neu.csye6200.parkingapp.dto.PaymentDTO;
import edu.neu.csye6200.parkingapp.model.Card;
import edu.neu.csye6200.parkingapp.model.Payment;
import edu.neu.csye6200.parkingapp.model.Rentee;
import edu.neu.csye6200.parkingapp.repository.CardRepository;
import edu.neu.csye6200.parkingapp.repository.PaymentRepository;
import edu.neu.csye6200.parkingapp.repository.RenteeRepository;
import edu.neu.csye6200.parkingapp.service.interfaces.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class PaymentService implements IPaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private RenteeRepository renteeRepository;


    @Autowired
    private StripeConfig stripeConfig;

    /**
     * Create a PaymentIntent and save payment details.
     */
    public String createPayment(PaymentDTO paymentDTO) throws StripeException {
        // Set Stripe API key
        Stripe.apiKey = stripeConfig.getSecretKey();

        // Fetch Card and Rentee entities
        Card card = cardRepository.findById(paymentDTO.getCardId())
                .orElseThrow(() -> new IllegalArgumentException("Card not found"));

        Rentee rentee = renteeRepository.findById(paymentDTO.getRenteeId())
                .orElseThrow(() -> new IllegalArgumentException("Rentee not found"));

        // Check or create a Stripe Customer
        String stripeCustomerId = card.getStripeCustomerId();
        if (stripeCustomerId == null) {
            // Create a new Stripe Customer
            Map<String, Object> customerParams = new HashMap<>();
            customerParams.put("name", card.getCardHolderName());
            Customer customer = Customer.create(customerParams);
            stripeCustomerId = customer.getId();

            // Save the Customer ID to the card
            card.setStripeCustomerId(stripeCustomerId);
            cardRepository.save(card); // Save the updated card entity
        }

        // Attach PaymentMethod to Customer if not already attached
        PaymentMethod paymentMethod = PaymentMethod.retrieve(card.getStripeCardId());
        paymentMethod.attach(PaymentMethodAttachParams.builder()
                .setCustomer(stripeCustomerId)
                .build());

        // Create Stripe PaymentIntent
        Map<String, Object> params = new HashMap<>();
        params.put("amount", convertToCents(paymentDTO.getAmount()));
        params.put("currency", "usd");
        params.put("customer", stripeCustomerId);
        params.put("payment_method", card.getStripeCardId());
        params.put("off_session", true);
        params.put("confirm", true);

        PaymentIntent intent = PaymentIntent.create(params);

        savePayment(paymentDTO, card, rentee, intent);
        return intent.getId();
    }

    private long convertToCents(BigDecimal amount) {
        return amount.multiply(new BigDecimal(100)).setScale(0, RoundingMode.HALF_UP).longValue();
    }

    private void savePayment(PaymentDTO paymentDTO, Card card, Rentee rentee, PaymentIntent intent) {
        Payment payment = new Payment();
        payment.setAmount(paymentDTO.getAmount());
        payment.setPaymentStatus("COMPLETED");
        payment.setStripePaymentId(intent.getId());
        payment.setCard(card);
        payment.setRentee(rentee);
        payment.setPaymentMethodId(intent.getPaymentMethod());
        payment.setCreatedAt(LocalDateTime.now());
        payment.setUpdatedAt(LocalDateTime.now());
        paymentRepository.save(payment);
    }

    public Optional<PaymentDTO> getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId).map(this::convertToDTO);
    }

    private PaymentDTO convertToDTO(Payment payment) {
        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());
        dto.setAmount(payment.getAmount());
        dto.setPaymentStatus(payment.getPaymentStatus());
        dto.setStripePaymentId(payment.getStripePaymentId());
        dto.setCardId(payment.getCard().getId());
        dto.setRenteeId(payment.getRentee().getId());
        return dto;
    }
}