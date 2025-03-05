package edu.neu.csye6200.parkingapp.model;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "payments")
@Data
public class Payment extends BaseEntity {

    @Column(nullable = false, precision = 7, scale = 2)
    private BigDecimal amount;

    @Column(length = 15)
    private String paymentStatus;

    @Column(nullable = false)
    private String stripePaymentId;

    @Column
    private String paymentMethodId;

    @ManyToOne // Many payments to one card
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @ManyToOne // Many payments to one rentee
    @JoinColumn(name = "rentee_id", nullable = false)
    private Rentee rentee;
}