package edu.neu.csye6200.parkingapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.Valid;
import java.util.List;

public class ParkingLocationDTO {
    private Long id;

    @NotBlank(message = "Street name is required")
    @Size(min = 5, max = 20, message = "Street name must be between 5 and 20 characters")
    private String street;

    @NotBlank(message = "City name is required")
    @Size(min = 5, max = 20, message = "City name must be between 5 and 20 characters")
    private String city;

    @NotBlank(message = "state name is required")
    @Size(min = 2, max = 20, message = "State name must be between 2 and 20 characters")
    private String state;

    @NotBlank(message = "Postal Code is required")
    @Size(min = 5, max = 5, message = "Postal Code must be 5 characters")
    private String postalcode;

    @NotBlank(message = "Country name is required")
    @Size(min = 3, max = 20, message = "Country name must be between 3 and 20 characters")
    private String country;

    private String latitude;
    private String longitude;

    private String uploadImage;

    private String parkingLocationImage;

    private Long renterId;

    @Valid
    private List<ParkingSpotDTO> parkingSpots;

    public ParkingLocationDTO(Long id, String street, String city, String state, String postalcode, String country, String latitude, String longitude, String parkingLocationImage, Long renterId) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.state = state;
        this.postalcode = postalcode;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.parkingLocationImage = parkingLocationImage;
        this.renterId = renterId;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {return street; }
    public void setStreet(String street) {this.street = street; }

    public String getCity() {return city; }
    public void setCity(String city) {this.city = city; }

    public String getState() {return state; }
    public void setState(String state) {this.state = state; }

    public String getPostalcode() {return postalcode; }
    public void setPostalcode(String postalcode) {this.postalcode = postalcode; }

    public String getCountry() {return country; }
    public void setCountry(String country) {this.country = country; }

    public String getLatitude() {return latitude; }
    public void setLatitude(String latitude) {this.latitude = latitude; }

    public String getLongitude() {return longitude; }
    public void setLongitude(String longitude) {this.longitude = longitude; }

    public String getParkingLocationImage() { return parkingLocationImage;}
    public void setParkingLocationImage(String parkingLocationImage) { this.parkingLocationImage = parkingLocationImage;}

    public Long getRenterId() {return renterId; }
    public void setRenterId(Long renterId) {this.renterId = renterId; }

    public String getUploadImage() {
        return uploadImage;
    }

    public void setUploadImage(String uploadImage) {
        this.uploadImage = uploadImage;
    }

    public List<ParkingSpotDTO> getParkingSpots() {
        return parkingSpots;
    }

    public void setParkingSpots(List<ParkingSpotDTO> parkingSpots) {
        this.parkingSpots = parkingSpots;
    }
}
