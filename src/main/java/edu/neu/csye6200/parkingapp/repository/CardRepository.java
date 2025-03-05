package edu.neu.csye6200.parkingapp.repository;

import edu.neu.csye6200.parkingapp.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByRenteeId(Long renteeId); // Find cards by user
}
