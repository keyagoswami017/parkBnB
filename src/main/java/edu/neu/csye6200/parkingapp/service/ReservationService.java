package edu.neu.csye6200.parkingapp.service;

import edu.neu.csye6200.parkingapp.dto.ReservationDTO;
import edu.neu.csye6200.parkingapp.model.*;
import edu.neu.csye6200.parkingapp.repository.*;
import edu.neu.csye6200.parkingapp.service.interfaces.IReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService implements IReservationService {

    @Autowired
    private  ReservationRepository reservationRepository;

    @Autowired
    private RenteeRepository renteeRepository;

    @Autowired
    private ParkingSpotRepository parkingSpotRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    public Optional<ReservationDTO> getReservationById(Long id) {
        Optional <Reservation> reservation = reservationRepository.findById(id);
        if (reservation.isPresent()) {
            Reservation res = reservation.get();
            ReservationDTO dto = new ReservationDTO();
            dto.setId(res.getId());
            dto.setRenteeId(res.getRentee().getId());
            dto.setParkingSpotId(res.getParkingSpot().getId());
            dto.setStartTime(res.getStartTime());
            dto.setEndTime(res.getEndTime());
            //dto.setConfirmationCode(res.getConfirmationCode());
            dto.setStatus(res.getStatus());
            dto.setPaymentId(res.getPayment().getId());
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    public List<ReservationDTO> getReservationsByRenteeId(Long renteeId) {
        List<Reservation> reservations = reservationRepository.findByRenteeId(renteeId);

        // Map to ReservationDTO
        return reservations.stream().map(reservation -> {
            ReservationDTO dto = new ReservationDTO();
            dto.setId(reservation.getId());
            dto.setStartTime(reservation.getStartTime());
            dto.setEndTime(reservation.getEndTime());
            dto.setStatus(reservation.getStatus());
            dto.setRenteeId(reservation.getRentee().getId());
            dto.setParkingSpotId(reservation.getParkingSpot().getId());
            dto.setPaymentId(reservation.getPayment().getId());

            // Assuming you have methods to get ParkingSpot and ParkingLocation details
            if (reservation.getParkingSpot() != null) {
                ReservationDTO.ParkingSpot spotDTO = new ReservationDTO.ParkingSpot();
                spotDTO.setSpotNumber(reservation.getParkingSpot().getSpotNumber());
                spotDTO.setParkingLocation(new ReservationDTO.ParkingLocation()); // Assuming this is a method to get the location
                dto.setParkingSpot(spotDTO);
            }

            // You can also set the parking location details if needed
            if (reservation.getParkingSpot() != null && reservation.getParkingSpot().getParkingLocation() != null) {
                ReservationDTO.ParkingLocation locationDTO = new ReservationDTO.ParkingLocation();
                locationDTO.setId(reservation.getParkingSpot().getParkingLocation().getId());
                locationDTO.setStreet(reservation.getParkingSpot().getParkingLocation().getStreet());
                locationDTO.setCity(reservation.getParkingSpot().getParkingLocation().getCity());
                locationDTO.setState(reservation.getParkingSpot().getParkingLocation().getState());
                locationDTO.setPostalcode(reservation.getParkingSpot().getParkingLocation().getPostalcode());
                locationDTO.setCountry(reservation.getParkingSpot().getParkingLocation().getCountry());
                locationDTO.setLatitude(reservation.getParkingSpot().getParkingLocation().getLatitude());
                locationDTO.setLongitude(reservation.getParkingSpot().getParkingLocation().getLongitude());
                locationDTO.setParkingLocationImage("parking_locations/" + reservation.getParkingSpot().getParkingLocation().getImageFileName());
                dto.getParkingSpot().setParkingLocation(locationDTO);
            }

            return dto;
        }).collect(Collectors.toList());
    }

    public ReservationDTO saveReservation(@Valid ReservationDTO reservationDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            throw new RuntimeException("Validation failed: " + bindingResult.getAllErrors());
        }

        Rentee rentee = renteeRepository.getById(reservationDTO.getRenteeId());
        ParkingSpot parkingSpot = parkingSpotRepository.getById(reservationDTO.getParkingSpotId());
        Payment payment = paymentRepository.findByStripePaymentId(reservationDTO.getPaymentIntentId());

        Reservation res = new Reservation();
        res.setStartTime(reservationDTO.getStartTime());
        res.setEndTime(reservationDTO.getEndTime());
        res.setStatus(reservationDTO.getStatus());
        res.setRentee(rentee);
        res.setParkingSpot(parkingSpot);
        res.setPayment(payment);

        // Save to database
        Reservation savedReservation = reservationRepository.save(res);

        ReservationDTO dto = new ReservationDTO();
        dto.setId(savedReservation.getId());
        dto.setRenteeId(savedReservation.getRentee().getId());
        dto.setParkingSpotId(savedReservation.getParkingSpot().getId());
        dto.setStartTime(savedReservation.getStartTime());
        dto.setEndTime(savedReservation.getEndTime());
        dto.setStatus(savedReservation.getStatus());
        dto.setPaymentId(savedReservation.getPayment().getId());
        return dto;
    }
}
