package edu.neu.csye6200.parkingapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ReviewDTO {

    private Long id;

    @NotBlank(message = "Description is required")
    @Size(min = 5, max = 100, message = "Description must be between 5 and 100 characters")
    private String comment;

    @NotBlank(message = "Rating is required")
    private double rating;

    @NotBlank(message = "Rentee ID is required")
    private Long renteeId;

    private String renteeName;

    @NotBlank(message = "Parking Location ID is required")
    private Long parkingLocationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public @NotBlank(message = "Rentee ID is required") Long getRenteeId() {
        return renteeId;
    }

    public void setRenteeId(@NotBlank(message = "Rentee ID is required") Long renteeId) {
        this.renteeId = renteeId;
    }

    public @NotBlank(message = "Parking Location ID is required") Long getParkingLocationId() {
        return parkingLocationId;
    }

    public void setParkingLocationId(@NotBlank(message = "Parking Location ID is required") Long parkingLocationId) {
        this.parkingLocationId = parkingLocationId;
    }

    public String getRenteeName() {
        return renteeName;
    }

    public void setRenteeName(String renteeName) {
        this.renteeName = renteeName;
    }

    @NotBlank(message = "Rating is required")
    public double getRating() {
        return rating;
    }

    public void setRating(@NotBlank(message = "Rating is required") double rating) {
        this.rating = rating;
    }
}

