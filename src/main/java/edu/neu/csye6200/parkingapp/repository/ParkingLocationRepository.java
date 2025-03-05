package edu.neu.csye6200.parkingapp.repository;

import edu.neu.csye6200.parkingapp.model.ParkingLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Repository
public interface ParkingLocationRepository extends JpaRepository<ParkingLocation, Long>{

    List<ParkingLocation> findByRenterId(Long renterId);

    List<ParkingLocation> findByCityContainingIgnoreCaseOrStreetContainingIgnoreCase(String city, String street);

    @Query(value = "SELECT * FROM parkinglocation WHERE " +
            "3958.8 * ACOS( " +
            "COS(RADIANS(?1)) * COS(RADIANS(CAST(latitude AS DOUBLE PRECISION))) * " +
            "COS(RADIANS(CAST(longitude AS DOUBLE PRECISION)) - RADIANS(?2)) + " +
            "SIN(RADIANS(?1)) * SIN(RADIANS(CAST(latitude AS DOUBLE PRECISION))) " +
            ") <= ?1",
            nativeQuery = true)
    List<ParkingLocation> findNearbyLocations(Double latitude, Double longitude, double radius);
}
