package edu.neu.csye6200.parkingapp.model;

import jakarta.persistence.*;

@Entity
@Table(name="reviews")
public class Review extends BaseEntity {

    @Column(name = "comment", nullable = false, length = 100)
    private String comment;

    @Column(name = "rating", nullable = false)
    private double rating;

    // Foreign key to Rentee
    @ManyToOne
    @JoinColumn(name = "rentee_id", nullable = false, foreignKey = @ForeignKey(name = "FK_RESERVATION_RENTEE"))
    private Rentee rentee;

    // Foreign key to ParkingLocation
    @ManyToOne
    @JoinColumn(name = "parking_location_id", nullable = false, foreignKey = @ForeignKey(name = "FK_RESERVATION_PARKING_LOCATION"))
    private ParkingLocation parkingLocation;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Rentee getRentee() {
        return rentee;
    }

    public void setRentee(Rentee rentee) {
        this.rentee = rentee;
    }

    public ParkingLocation getParkingLocation() {
        return parkingLocation;
    }

    public void setParkingLocation(ParkingLocation parkingLocation) {
        this.parkingLocation = parkingLocation;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
