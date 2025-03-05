package edu.neu.csye6200.parkingapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CardDTO {

    private Long id;

    @NotBlank(message = "Last 4 digits of the card number is required")
    @Size(min = 4, max = 4, message = "Last 4 digits of the card number must be 4 characters")
    private String last4; // Last 4 digits of the card number

    @NotBlank(message = "Card type is required")
    private String cardType; // e.g., "Visa", "MasterCard"

    @NotBlank(message = "Stripe card ID is required")
    private String stripeCardId; // Stripe's unique ID for the card

    @NotNull(message = "Expiry date is required")
    private String expiryDate;

    @NotNull(message = "Rentee ID is required")
    private Long renteeId; // Reference to the Rentee entity

    private String cardHolderName;
}
