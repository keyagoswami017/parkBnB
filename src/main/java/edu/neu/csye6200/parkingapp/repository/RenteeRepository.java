package edu.neu.csye6200.parkingapp.repository;

import edu.neu.csye6200.parkingapp.model.Rentee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RenteeRepository extends JpaRepository<Rentee, Long> {
    Optional<Rentee> findByEmail(String email);
}
