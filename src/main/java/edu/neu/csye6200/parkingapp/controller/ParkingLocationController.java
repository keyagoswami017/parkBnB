package edu.neu.csye6200.parkingapp.controller;

import edu.neu.csye6200.parkingapp.dto.ApiResponse;
import edu.neu.csye6200.parkingapp.dto.ParkingLocationDTO;
import edu.neu.csye6200.parkingapp.dto.ParkingSpotDTO;
import edu.neu.csye6200.parkingapp.dto.ReviewDTO;
import edu.neu.csye6200.parkingapp.service.ParkingLocationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import edu.neu.csye6200.parkingapp.model.ParkingLocation;
import java.io.IOException;

@RestController
@RequestMapping("/api/parkinglocation")
public class ParkingLocationController {

    @Autowired
    private ParkingLocationService parkingLocationService;

    @GetMapping("/{id}")
    public ResponseEntity<ParkingLocationDTO> getParkingLocation(@PathVariable Long id) {
        Optional<ParkingLocationDTO> parkingLocationDTO = parkingLocationService.getParkingLocationById(id);
        if (parkingLocationDTO.isPresent()) {
            return ResponseEntity.ok(parkingLocationDTO.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<ParkingLocationDTO>> getNearbyParkingLocations(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam Double radius) {
        try {
            List<ParkingLocationDTO> nearbyLocations = parkingLocationService.findNearbyLocations(latitude, longitude, radius);
            return ResponseEntity.ok(nearbyLocations);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Search for parking locations based on user input or coordinates
    @GetMapping("/search")
    public ResponseEntity<List<ParkingLocationDTO>> searchParkingLocations(
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "latitude", required = false) Double latitude,
            @RequestParam(value = "longitude", required = false) Double longitude,
            @RequestParam(value = "radius", defaultValue = "1") double radius) {

        List<ParkingLocationDTO> parkingLocations;

        if (query != null && !query.isEmpty()) {
            parkingLocations = parkingLocationService.searchByQuery(query);
        } else if (latitude != null && longitude != null) {
            parkingLocations = parkingLocationService.searchByCoordinates(latitude, longitude, radius);
        } else {
            return ResponseEntity.badRequest().build();
        }

        if (parkingLocations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(parkingLocations);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ApiResponse<ParkingLocationDTO>> createParkingLocation(
            @Valid @RequestBody ParkingLocationDTO parkingLocationDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Validation failed"));
        }

        try {
            ParkingLocationDTO savedParkingLocation = parkingLocationService.saveParkingLocation(parkingLocationDTO, bindingResult);
            return ResponseEntity.ok(new ApiResponse<>(true, savedParkingLocation));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Error uploading file: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "An unexpected error occurred: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}/available-spots")
    public ResponseEntity<List<ParkingSpotDTO>> getAvailableParkingSpots(@PathVariable Long id) {
        List<ParkingSpotDTO> availableSpots = parkingLocationService.getAvailableSpots(id);
        return ResponseEntity.ok(availableSpots);
    }

    @GetMapping("/{locationId}/reviews")
    public ResponseEntity<List<ReviewDTO>> getReviews(@PathVariable Long locationId) {
        List<ReviewDTO> reviews = parkingLocationService.getReviewsForParkingLocation(locationId);
        if (reviews.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/renter/{renterId}")
    public List<ParkingLocation> getParkingLocationsByRenter(@PathVariable Long renterId) {
        return parkingLocationService.getParkingLocationsByRenter(renterId);
    }

    @PutMapping("/{id}")
    public ParkingLocation saveOrUpdateParkingLocation(@RequestBody ParkingLocation parkingLocation) {
        return parkingLocationService.saveOrUpdateParkingLocation(parkingLocation);
    }

    @DeleteMapping("/{id}")
    public void deleteParkingLocation(@PathVariable Long id) {
        parkingLocationService.deleteParkingLocation(id);
    }
}
