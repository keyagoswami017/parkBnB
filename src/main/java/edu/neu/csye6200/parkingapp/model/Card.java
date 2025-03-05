package edu.neu.csye6200.parkingapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "cards")
public class Card extends BaseEntity {

    @Column(length = 4, nullable = false)
    private String last4; // Last 4 digits of the card number

    @Column(length = 30, nullable = false)
    private String cardType; // e.g., "Visa", "MasterCard"

    @Column(nullable = false)
    private String stripeCardId; // ID provided by Stripe for saved card

    @Column(nullable = false)
    private String expiryDate; // Expiry date of the card

    private String cardHolderName;

    private String stripeCustomerId;

    @ManyToOne
    @JoinColumn(name = "rentee_id", nullable = false)
    private Rentee rentee;
}
