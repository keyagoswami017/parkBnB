package edu.neu.csye6200.parkingapp.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ParkingSpotDTO {
    private long id;

    @NotBlank(message = "Spot number is required")
    @Size(min = 2, max = 10, message = "Spot Number must be between 2 and 10 characters")
    private String spotNumber;

    @NotBlank(message = "Spot Type is required")
    @Size(min = 2, max = 15, message = "Spot type must be between 2 and 15 characters")
    private String spotType;

    @NotBlank(message = "Availability Status is required")
    private boolean isAvailable;

    @NotBlank(message = "Price per hour is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price per hour must be greater than 0")
    @DecimalMax(value = "1000.0", message = "Price per hour must be less than or equal to 1000")
    private double pricePerHour;

    private Long parkingLocationId;

    public ParkingSpotDTO(long id,String spotNumber, String spotType, boolean isAvailable, double pricePerHour, Long parkingLocationId) {

        this.id = id;
        this.spotNumber = spotNumber;
        this.spotType = spotType;
        this.isAvailable = isAvailable;
        this.pricePerHour = pricePerHour;
        this.parkingLocationId = parkingLocationId;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getSpotNumber() { return spotNumber; }
    public void setSpotNumber(String spotNumber) { this.spotNumber = spotNumber; }

    public String getSpotType() { return spotType; }
    public void setSpotType(String spotType) { this.spotType = spotType; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { this.isAvailable = available; }

    public double getPricePerHour() { return pricePerHour; }
    public void setPricePerHour(double pricePerHour) { this.pricePerHour = pricePerHour; }

    public Long getParkingLocationId() { return parkingLocationId; }
    public void setParkingLocationId(Long parkingLocationId) { this.parkingLocationId = parkingLocationId;}

}
