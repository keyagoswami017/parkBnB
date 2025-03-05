package edu.neu.csye6200.parkingapp.controller;

import edu.neu.csye6200.parkingapp.dto.ParkingSpotDTO;
import edu.neu.csye6200.parkingapp.model.ParkingSpot;
import edu.neu.csye6200.parkingapp.service.ParkingSpotService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/parkingspot")
public class ParkingSpotController {
    @Autowired
    private ParkingSpotService parkingSpotService;

    @GetMapping("/{id}")
    public ResponseEntity<ParkingSpotDTO> getParkingSpot(@PathVariable Long id) {
        Optional<ParkingSpotDTO> parkingSpotDTO = parkingSpotService.getParkingSpotById(id);
        if (parkingSpotDTO.isPresent()) {
            return ResponseEntity.ok(parkingSpotDTO.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ParkingSpotDTO> createParkingSpot(@Valid @RequestBody ParkingSpotDTO parkingSpotDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(null);
        }
        ParkingSpotDTO savedParkingSpot = parkingSpotService.saveParkingLocation(parkingSpotDTO,bindingResult);
        return ResponseEntity.ok(savedParkingSpot);
    }

    // Fetch parking spots by parking location ID
    @GetMapping("/location/{locationId}")
    public List<ParkingSpot> getParkingSpotsByLocation(@PathVariable Long locationId) {
        return parkingSpotService.getParkingSpotsByLocation(locationId);
    }

    // Save or update parking spot
    @PutMapping("/{id}")
    public ParkingSpot saveOrUpdateParkingSpot(@RequestBody ParkingSpot parkingSpot) {
        return parkingSpotService.saveOrUpdateParkingSpot(parkingSpot);
    }

    // Delete parking spot by ID
    @DeleteMapping("/{id}")
    public void deleteParkingSpot(@PathVariable Long id) {
        parkingSpotService.deleteParkingSpot(id);
    }

}
