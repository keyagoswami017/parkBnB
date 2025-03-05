package edu.neu.csye6200.parkingapp.service;


import edu.neu.csye6200.parkingapp.dto.ParkingSpotDTO;
import edu.neu.csye6200.parkingapp.model.ParkingSpot;
import edu.neu.csye6200.parkingapp.model.ParkingLocation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.neu.csye6200.parkingapp.repository.ParkingLocationRepository;
import edu.neu.csye6200.parkingapp.repository.ParkingSpotRepository;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingSpotService {

    @Autowired
    private  ParkingLocationRepository parkingLocationRepository;
    @Autowired
    private ParkingSpotRepository parkingSpotRepository;


    public List<ParkingSpot> getParkingSpotsByLocation(Long locationId) {
        return parkingSpotRepository.findByParkingLocationId(locationId);
    }

    public ParkingSpot saveOrUpdateParkingSpot(ParkingSpot parkingSpot) {
        return parkingSpotRepository.save(parkingSpot);
    }

    public void deleteParkingSpot(Long id) {
        parkingSpotRepository.deleteById(id);
    }

    public Optional<ParkingSpotDTO> getParkingSpotById(Long id) {
        Optional<ParkingSpot> parkingSpot = parkingSpotRepository.findById(id);
        if (parkingSpot.isPresent()) {
            ParkingSpot ps = parkingSpot.get();
            Long parkingLocationId = (ps.getParkingLocation() != null) ? ps.getParkingLocation().getId() : null; // Retrieve parking location ID if available
            ParkingSpotDTO parkingSpotDTO = new ParkingSpotDTO(ps.getId(),ps.getSpotNumber(),ps.getSpotType(),ps.isAvailable(),ps.getPricePerHour(),parkingLocationId);
            return Optional.of(parkingSpotDTO);
        }
        return Optional.empty();
    }

    public ParkingSpotDTO saveParkingLocation(@Valid ParkingSpotDTO parkingSpotDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException("Validation failed: " + bindingResult.getAllErrors());
        }

        ParkingSpot parkingSpot = new ParkingSpot();
        parkingSpot.setSpotNumber(parkingSpotDTO.getSpotNumber());
        parkingSpot.setSpotType(parkingSpotDTO.getSpotType());
        parkingSpot.setAvailable(parkingSpotDTO.isAvailable());
        parkingSpot.setPricePerHour(parkingSpotDTO.getPricePerHour());

        if (parkingSpotDTO.getParkingLocationId() != null) {
            ParkingLocation r = parkingLocationRepository.findById(parkingSpotDTO.getParkingLocationId())
                    .orElseThrow(() -> new RuntimeException("Renter not found with ID: " + parkingSpotDTO.getParkingLocationId()));
            parkingSpot.setParkingLocation(r);
        }

        ParkingSpot saveParkingSpot = parkingSpotRepository.save(parkingSpot);

        return new ParkingSpotDTO(saveParkingSpot.getId(),saveParkingSpot.getSpotNumber(),saveParkingSpot.getSpotType(),saveParkingSpot.isAvailable(),saveParkingSpot.getPricePerHour(),saveParkingSpot.getParkingLocation().getId());
    }
}
