package edu.neu.csye6200.parkingapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "parkingspot")
public class ParkingSpot extends BaseEntity {

    @Column(name = "spotNumber",nullable = false , length = 10)
    private  String spotNumber;

    @Column(name = "spotType",nullable = false , length = 15)
    private  String spotType;

    @Column(name = "isAvailable",nullable = false)
    private  boolean isAvailable;

    @Column(name = "pricePerHour",nullable = false)
    private  double pricePerHour;

    // Foreign Key relationship with Parking Location
    @ManyToOne
    @JoinColumn(
            name = "parkingLocation_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_parkingspot_location", value = ConstraintMode.CONSTRAINT)
    )
    private ParkingLocation parkingLocation;

    public String getSpotNumber() { return spotNumber;}
    public void setSpotNumber(String spotNumber) { this.spotNumber = spotNumber;}

    public String getSpotType() { return spotType;}
    public void setSpotType(String spotType) { this.spotType = spotType;}

    public boolean isAvailable() { return isAvailable;}
    public void setAvailable(boolean isAvailable) { this.isAvailable = isAvailable;}

    public double getPricePerHour() { return pricePerHour;}
    public void setPricePerHour(double pricePerHour) { this.pricePerHour = pricePerHour;}

    public ParkingLocation getParkingLocation() { return parkingLocation;}
    public void setParkingLocation(ParkingLocation parkingLocation) { this.parkingLocation = parkingLocation;}

}
