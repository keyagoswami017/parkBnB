package edu.neu.csye6200.parkingapp.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "reservation")
public class Reservation extends BaseEntity {

    @Column(name = "start_time", nullable = false, length = 50)
    private Timestamp startTime;

    @Column(name = "end_time", nullable = false, length = 50)
    private Timestamp endTime;

    @Column(name = "status", nullable = false, length = 15)
    private String status;

    // Foreign key to Rentee
    @ManyToOne
    @JoinColumn(name = "rentee_id", nullable = false, foreignKey = @ForeignKey(name = "FK_RESERVATION_RENTEE"))
    private Rentee rentee;

    // Foreign key to ParkingSpot
    @ManyToOne
    @JoinColumn(name = "parking_spot_id", nullable = false, foreignKey = @ForeignKey(name = "FK_RESERVATION_PARKING_SPOT"))
    private ParkingSpot parkingSpot;

    // Foreign key to Payment
    @ManyToOne
    @JoinColumn(name = "payment_id", foreignKey = @ForeignKey(name = "FK_RESERVATION_PAYMENT"))
    private Payment payment;

    // Getters and Setters
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Rentee getRentee() {
        return rentee;
    }

    public void setRentee(Rentee rentee) {
        this.rentee = rentee;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
