package edu.neu.csye6200.parkingapp.controller;

import edu.neu.csye6200.parkingapp.dto.ReservationDTO;
import edu.neu.csye6200.parkingapp.service.interfaces.IReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private IReservationService reservationService;

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long id) {
        Optional<ReservationDTO> reservationDTO = reservationService.getReservationById(id);
        if (reservationDTO.isPresent()) {
            return ResponseEntity.ok(reservationDTO.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/rentee/{renteeId}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByRenteeId(@PathVariable Long renteeId) {
        List<ReservationDTO> reservations = reservationService.getReservationsByRenteeId(renteeId);
        if (reservations.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservations);
    }

    @PostMapping
    public ResponseEntity<ReservationDTO> createReservation(@Valid @RequestBody ReservationDTO reservationDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(null);
        }

        ReservationDTO savedReservation = reservationService.saveReservation(reservationDTO, bindingResult);
        return ResponseEntity.ok(savedReservation);
    }

}
