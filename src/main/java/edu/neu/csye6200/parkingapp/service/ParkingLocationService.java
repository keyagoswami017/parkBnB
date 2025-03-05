package edu.neu.csye6200.parkingapp.service;

import edu.neu.csye6200.parkingapp.dto.ParkingLocationDTO;
import edu.neu.csye6200.parkingapp.dto.ParkingSpotDTO;
import edu.neu.csye6200.parkingapp.dto.ReviewDTO;
import edu.neu.csye6200.parkingapp.model.ParkingLocation;
import edu.neu.csye6200.parkingapp.model.ParkingSpot;
import edu.neu.csye6200.parkingapp.model.Renter;
import edu.neu.csye6200.parkingapp.model.Review;
import edu.neu.csye6200.parkingapp.repository.ParkingSpotRepository;
import edu.neu.csye6200.parkingapp.repository.ReviewRepository;
import edu.neu.csye6200.parkingapp.service.external.GeocodingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import edu.neu.csye6200.parkingapp.repository.ParkingLocationRepository;
import edu.neu.csye6200.parkingapp.repository.RenterRepository;
import org.springframework.validation.BindingResult;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParkingLocationService {
    @Autowired
    private  ParkingLocationRepository parkingLocationRepository;

    @Autowired
    private RenterRepository renterRepository;

    @Autowired
    private GeocodingService geocodingService;

    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    @Autowired
    private ReviewRepository reviewRepository;


    @Value("${upload.dir.parking_locations}")
    private String uploadDirForParkingLocations;

    public List<ParkingLocation> getParkingLocationsByRenter(Long renterId) {
        return parkingLocationRepository.findByRenterId(renterId);
    }

    public ParkingLocation saveOrUpdateParkingLocation(ParkingLocation parkingLocation) {
        return parkingLocationRepository.save(parkingLocation);
    }

    public void deleteParkingLocation(Long id) {
        ParkingLocation location = parkingLocationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parking Location not found"));
        parkingLocationRepository.delete(location);
    }


    public Optional<ParkingLocationDTO> getParkingLocationById(Long id) {
        Optional<ParkingLocation> parkingLocation = parkingLocationRepository.findById(id);
        if (parkingLocation.isPresent()) {
            ParkingLocation p = parkingLocation.get();
            Long renterId = (p.getRenter() != null) ? p.getRenter().getId() : null; // Retrieve renter ID if available
            String imagePath = "parking_locations/" + p.getImageFileName();
            ParkingLocationDTO parkingLocationDTO = new ParkingLocationDTO(p.getId(),p.getStreet(),p.getCity(),p.getPostalcode(),p.getState(),p.getCountry(),p.getLatitude(),p.getLongitude(), imagePath, renterId);
            return Optional.of(parkingLocationDTO);
        }
        return Optional.empty();
    }


    public List<ParkingLocationDTO> searchByQuery(String query) {
        List<ParkingLocation> locations = parkingLocationRepository.findByCityContainingIgnoreCaseOrStreetContainingIgnoreCase(
                query, query);

        return mapToDTOs(locations);
    }

    public List<ParkingLocationDTO> findNearbyLocations(Double latitude, Double longitude, Double radius) {
        List<ParkingLocation> allLocations = parkingLocationRepository.findAll();
        return allLocations.stream()
                .filter(location -> {
                    try {
                        // Parse latitude and longitude from String to Double
                        double locationLat = Double.parseDouble(location.getLatitude());
                        double locationLon = Double.parseDouble(location.getLongitude());
                        return calculateDistance(latitude, longitude, locationLat, locationLon) <= radius;
                    } catch (NumberFormatException e) {
                        // Handle invalid format, if necessary
                        return false;
                    }
                })
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int EARTH_RADIUS = 6371; // Radius in kilometers
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c; // Distance in kilometers
    }

    private ParkingLocationDTO convertToDTO(ParkingLocation location) {
        // Map ParkingLocation to ParkingLocationDTO

        String imagePath = "parking_locations/" + location.getImageFileName();
        return new ParkingLocationDTO( location.getId(),
                location.getStreet(),
                location.getCity(),
                location.getPostalcode(),
                location.getState(),
                location.getCountry(),
                location.getLatitude(),
                location.getLongitude(),
                imagePath,
                location.getRenter().getId());
    }

    public List<ParkingLocationDTO> searchByCoordinates(Double latitude, Double longitude, double radius) {
        List<ParkingLocation> locations = parkingLocationRepository.findNearbyLocations(latitude, longitude, radius);
        return mapToDTOs(locations);
    }

    private List<ParkingLocationDTO> mapToDTOs(List<ParkingLocation> locations) {
        List<ParkingLocationDTO> dtos = new ArrayList<>();
        for (ParkingLocation location : locations) {
            dtos.add(new ParkingLocationDTO(
                    location.getId(),
                    location.getStreet(),
                    location.getCity(),
                    location.getPostalcode(),
                    location.getState(),
                    location.getCountry(),
                    location.getLatitude(),
                    location.getLongitude(),
                    uploadDirForParkingLocations + location.getImageFileName(),
                    location.getRenter().getId()
            ));
        }
        return dtos;
    }


    public ParkingLocationDTO saveParkingLocation(@Valid ParkingLocationDTO parkingLocationDTO, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            throw new RuntimeException("Validation failed: " + bindingResult.getAllErrors());
        }

        // Convert DTO to entity
        ParkingLocation parkingLocation = new ParkingLocation();
        parkingLocation.setStreet(parkingLocationDTO.getStreet());
        parkingLocation.setCity(parkingLocationDTO.getCity());
        parkingLocation.setPostalcode(parkingLocationDTO.getPostalcode());
        parkingLocation.setState(parkingLocationDTO.getState());
        parkingLocation.setCountry(parkingLocationDTO.getCountry());

        Renter r = renterRepository.findById(parkingLocationDTO.getRenterId())
                 .orElseThrow(() -> new RuntimeException("Renter not found with ID: " + parkingLocationDTO.getRenterId()));
        parkingLocation.setRenter(r);

        // Get latitude and longitude using Geocoding Service
        geocodingService.getCoordinates(parkingLocation);

        // Save to database
        ParkingLocation savedParkingLocation = parkingLocationRepository.save(parkingLocation);

        String path = uploadDirForParkingLocations + savedParkingLocation.getImageFileName();

        // Decode the base64 image to a file
        if (parkingLocationDTO.getUploadImage() != null) {
            byte[] imageBytes = Base64.getDecoder().decode(parkingLocationDTO.getUploadImage());
            String imagePath = saveImage(imageBytes, path);
        }

        // Save the parking spots
        List<ParkingSpotDTO> parkingSpots = parkingLocationDTO.getParkingSpots();
        for (ParkingSpotDTO spotDTO : parkingSpots) {
            ParkingSpot parkingSpot = new ParkingSpot();
            parkingSpot.setSpotNumber(spotDTO.getSpotNumber());
            parkingSpot.setSpotType(spotDTO.getSpotType());
            parkingSpot.setPricePerHour(spotDTO.getPricePerHour());
            parkingSpot.setAvailable(spotDTO.isAvailable());
            parkingSpot.setParkingLocation(savedParkingLocation); // Associate with the saved parking location
            parkingSpotRepository.save(parkingSpot);
        }

        // Return the saved entity as DTO
        return new ParkingLocationDTO(savedParkingLocation.getId(),savedParkingLocation.getStreet(),savedParkingLocation.getCity(),savedParkingLocation.getPostalcode(),savedParkingLocation.getState(),savedParkingLocation.getCountry(),savedParkingLocation.getLatitude(),savedParkingLocation.getLongitude(), path, savedParkingLocation.getRenter().getId());
    }

    public List<ParkingSpotDTO> getAvailableSpots(Long locationId) {
        List<ParkingSpot> parkingSpots = parkingSpotRepository.findByParkingLocationIdAndIsAvailable(locationId, true);
        List<ParkingSpotDTO> parkingSpotDTOList = new ArrayList<>();

        for (ParkingSpot ps : parkingSpots) {
            Long parkingLocationId = ps.getParkingLocation().getId();
            ParkingSpotDTO parkingSpotDTO = new ParkingSpotDTO(
                    ps.getId(),
                    ps.getSpotNumber(),
                    ps.getSpotType(),
                    ps.isAvailable(),
                    ps.getPricePerHour(),
                    parkingLocationId
            );
            parkingSpotDTOList.add(parkingSpotDTO);
        }

        return parkingSpotDTOList;
    }

    public List<ReviewDTO> getReviewsForParkingLocation(Long locationId) {

        List<Review> reviews = reviewRepository.findByParkingLocationId(locationId);

        List<ReviewDTO> reviewDTOs = new ArrayList<>();
        for (Review review : reviews) {
            ReviewDTO dto = new ReviewDTO();
            dto.setId(review.getId());
            dto.setComment(review.getComment());
            dto.setRating(review.getRating());
            dto.setRenteeId(review.getRentee().getId());
            dto.setParkingLocationId(review.getParkingLocation().getId());
            dto.setRenteeName(review.getRentee().getFirstName() + " " + review.getRentee().getLastName());
            reviewDTOs.add(dto);
        }
        return reviewDTOs;
    }

    private String saveImage(byte[] imageBytes, String path) throws IOException {
        String imagePath = path;
        try (FileOutputStream fos = new FileOutputStream(imagePath)) {
            fos.write(imageBytes);
        }
        return imagePath;
    }
}
