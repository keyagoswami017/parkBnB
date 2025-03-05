package edu.neu.csye6200.parkingapp.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.sql.Timestamp;

public class ReservationDTO {
    private Long id;

    @NotBlank(message = "Start Time is required")
    private Timestamp startTime;

    @NotBlank(message = "End Time is required")
    private Timestamp endTime;

    @NotBlank(message = "Status is required")
    @Size( max = 15, message = "Status can have max 15 characters")
    private String status;

    @NotBlank(message = "Rentee ID is required")
    private Long renteeId;

    @NotBlank(message = "Parking Spot ID is required")
    private Long parkingSpotId;

    @NotBlank(message = "Payment ID is required")
    private Long paymentId;

    private String paymentIntentId;

    private ParkingSpot parkingSpot;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getRenteeId() {
        return renteeId;
    }

    public void setRenteeId(Long renteeId) {
        this.renteeId = renteeId;
    }

    public Long getParkingSpotId() {
        return parkingSpotId;
    }

    public void setParkingSpotId(Long parkingSpotId) {
        this.parkingSpotId = parkingSpotId;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public String getPaymentIntentId() {
        return paymentIntentId;
    }

    public void setPaymentIntentId(String paymentIntentId) {
        this.paymentIntentId = paymentIntentId;
    }

    public static class ParkingSpot{
        private String spotNumber;
        ParkingLocation parkingLocation;

        public String getSpotNumber() {
            return spotNumber;
        }

        public void setSpotNumber(String spotNumber) {
            this.spotNumber = spotNumber;
        }

        public ParkingLocation getParkingLocation() {
            return parkingLocation;
        }

        public void setParkingLocation(ParkingLocation parkingLocation) {
            this.parkingLocation = parkingLocation;
        }
    }


    public static class ParkingLocation{
        private Long id;
        private String street;
        private String city;
        private String state;
        private String postalcode;
        private String country;
        private String latitude;
        private String longitude;
        private String parkingLocationImage;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getPostalcode() {
            return postalcode;
        }

        public void setPostalcode(String postalcode) {
            this.postalcode = postalcode;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getParkingLocationImage() {
            return parkingLocationImage;
        }

        public void setParkingLocationImage(String parkingLocationImage) {
            this.parkingLocationImage = parkingLocationImage;
        }
    }
}
