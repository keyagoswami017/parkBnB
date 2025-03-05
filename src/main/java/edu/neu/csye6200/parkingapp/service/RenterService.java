package edu.neu.csye6200.parkingapp.service;

import edu.neu.csye6200.parkingapp.dto.RenterDTO;
import edu.neu.csye6200.parkingapp.model.Renter;
import edu.neu.csye6200.parkingapp.service.interfaces.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.neu.csye6200.parkingapp.repository.RenterRepository;
import org.springframework.validation.BindingResult;
import org.mindrot.jbcrypt.BCrypt;
import java.util.Optional;

@Service
public class RenterService implements IUserService<RenterDTO> {

    @Autowired
    private RenterRepository renterRepository;

    @Override
    public Optional<RenterDTO> findUserByEmailAndPassword(String email, String password) {
        Optional<Renter> renter = renterRepository.findByEmail(email);
        if (renter.isPresent()) {
            Renter existingRenter = renter.get();
            if (BCrypt.checkpw(password, existingRenter.getPassword())) {
                // Password matches, return the user as DTO
                RenterDTO renterDTO = new RenterDTO(existingRenter.getId(), existingRenter.getFirstName(), existingRenter.getLastName(), existingRenter.getPassword(), existingRenter.getEmail(), existingRenter.getPhone());
                return Optional.of(renterDTO);
            }
        }
        return Optional.empty();  // Invalid login credentials
    }

    @Override
    public Optional<RenterDTO> getUserById(Long id) {
        Optional<Renter> renter = renterRepository.findById(id);
        if (renter.isPresent()) {
            Renter r = renter.get();
            RenterDTO renterDTO = new RenterDTO(r.getId(), r.getFirstName(), r.getLastName(), r.getPassword(), r.getEmail(), r.getPhone());
            return Optional.of(renterDTO);
        }
        return Optional.empty();
    }

    @Override
    public RenterDTO saveUser(@Valid RenterDTO user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            throw new RuntimeException("Validation failed: " + bindingResult.getAllErrors());
        }

        // Convert DTO to entity
        Renter renter = new Renter();
        renter.setFirstName(user.getFirstName());
        renter.setLastName(user.getLastName());
        renter.setPassword(user.getPassword());
        renter.setEmail(user.getEmail());
        renter.setPhone(user.getPhone());

        // Save to database
        Renter savedRenter = renterRepository.save(renter);

        // Return the saved entity as DTO
        return new RenterDTO(savedRenter.getId(), savedRenter.getFirstName(), savedRenter.getLastName(), savedRenter.getPassword(), savedRenter.getEmail(), savedRenter.getPhone());

    }
}
