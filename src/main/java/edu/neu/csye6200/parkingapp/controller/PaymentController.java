package edu.neu.csye6200.parkingapp.controller;

import com.stripe.exception.StripeException;
import edu.neu.csye6200.parkingapp.dto.PaymentDTO;
import edu.neu.csye6200.parkingapp.service.interfaces.IPaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private IPaymentService paymentService;

    /**
     * Endpoint to create a payment.
     *
     * @param paymentDTO the payment details
     * @return ResponseEntity with PaymentDTO and HTTP status
     */
    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@RequestBody @Valid PaymentDTO paymentDTO) {
        try {
            String paymentIntentId = paymentService.createPayment(paymentDTO);
            return new ResponseEntity<>(paymentIntentId, HttpStatus.CREATED);
        } catch (StripeException e) {
            System.out.println("Stripe error: " + e.getMessage());
            return new ResponseEntity<>("Stripe error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to retrieve a payment by ID.
     *
     * @param id the Payment ID
     * @return ResponseEntity with PaymentDTO or 404 status
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getPaymentById(@PathVariable Long id) {
        try {
            Optional<PaymentDTO> payment = paymentService.getPaymentById(id);
            return payment.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}