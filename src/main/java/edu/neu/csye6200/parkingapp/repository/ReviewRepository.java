package edu.neu.csye6200.parkingapp.repository;

import edu.neu.csye6200.parkingapp.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByParkingLocationId(Long locationId);
}
