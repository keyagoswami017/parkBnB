package edu.neu.csye6200.parkingapp.service;

import edu.neu.csye6200.parkingapp.dto.RenteeDTO;
import edu.neu.csye6200.parkingapp.model.Rentee;
import edu.neu.csye6200.parkingapp.repository.RenteeRepository;
import edu.neu.csye6200.parkingapp.service.interfaces.IUserService;
import jakarta.validation.Valid;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
public class RenteeService implements IUserService<RenteeDTO> {

    @Autowired
    private RenteeRepository renteeRepository;

    @Override
    public Optional<RenteeDTO> findUserByEmailAndPassword(String email, String password) {
        Optional<Rentee> rentee = renteeRepository.findByEmail(email);
        if (rentee.isPresent()) {
            Rentee existingRentee = rentee.get();
            if (BCrypt.checkpw(password, existingRentee.getPassword())) {
                RenteeDTO renteeDTO = new RenteeDTO(existingRentee.getId(), existingRentee.getFirstName(),
                        existingRentee.getLastName(), existingRentee.getPassword(), existingRentee.getEmail(),
                        existingRentee.getPhone());
                return Optional.of(renteeDTO);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<RenteeDTO> getUserById(Long id) {
        Optional<Rentee> rentee = renteeRepository.findById(id);
        if (rentee.isPresent()) {
            Rentee r = rentee.get();
            RenteeDTO renteeDTO = new RenteeDTO(r.getId(), r.getFirstName(), r.getLastName(), r.getPassword(), r.getEmail(), r.getPhone());
            return Optional.of(renteeDTO);
        }
        return Optional.empty();
    }

    @Override
    public RenteeDTO saveUser(@Valid RenteeDTO user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            throw new RuntimeException("Validation failed: " + bindingResult.getAllErrors());
        }

        Rentee rentee = new Rentee();
        rentee.setFirstName(user.getFirstName());
        rentee.setLastName(user.getLastName());
        rentee.setPassword(user.getPassword());
        rentee.setEmail(user.getEmail());
        rentee.setPhone(user.getPhone());

        Rentee savedRentee = renteeRepository.save(rentee);

        return new RenteeDTO(savedRentee.getId(), savedRentee.getFirstName(), savedRentee.getLastName(), savedRentee.getPassword(), savedRentee.getEmail(), savedRentee.getPhone());

    }
}
