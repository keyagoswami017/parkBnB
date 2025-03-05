package edu.neu.csye6200.parkingapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentDTO {
    private Long id; // Auto-generated, so no validation needed

    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    @NotBlank(message = "Payment status is required")
    private String paymentStatus;

    @NotBlank(message = "Stripe payment ID is required")
    private String stripePaymentId;

    @NotNull(message = "Card ID is required")
    private Long cardId;

    @NotNull(message = "Rentee ID is required")
    private Long renteeId;
}
