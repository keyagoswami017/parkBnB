package edu.neu.csye6200.parkingapp.controller;

import edu.neu.csye6200.parkingapp.dto.ApiResponse;
import edu.neu.csye6200.parkingapp.dto.RenteeDTO;
import edu.neu.csye6200.parkingapp.dto.RenterDTO;
import edu.neu.csye6200.parkingapp.model.Rentee;
import edu.neu.csye6200.parkingapp.model.Renter;
import edu.neu.csye6200.parkingapp.repository.RenteeRepository;
import edu.neu.csye6200.parkingapp.repository.RenterRepository;
import edu.neu.csye6200.parkingapp.service.RenteeService;
import edu.neu.csye6200.parkingapp.service.interfaces.IUserService;
import edu.neu.csye6200.parkingapp.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/rentees")
public class RenteeController {

    @Autowired
    private IUserService<RenteeDTO> renteeService;

    @Autowired
    private RenterRepository renterRepository;

    @Autowired
    private RenteeRepository renteeRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/{id}")
    public ResponseEntity<RenteeDTO> getRentee(@PathVariable Long id) {
        Optional<RenteeDTO> renteeDTO = renteeService.getUserById(id);
        if (renteeDTO.isPresent()) {
            return ResponseEntity.ok(renteeDTO.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody RenteeDTO renteeDTO) {
        Optional<RenteeDTO> existingUser = renteeService.findUserByEmailAndPassword(renteeDTO.getEmail(), renteeDTO.getPassword());

        if (existingUser.isPresent()) {
            RenteeDTO user = existingUser.get();

            // Generate JWT token
            String token = jwtUtil.generateToken(user.getEmail());

            // Build response
            Map<String, Object> response = new HashMap<>();
            response.put("id", user.getId());
            response.put("email", user.getEmail());
            response.put("token", token);

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String authHeader) {
        System.out.println("Received Authorization Header: " + authHeader);
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7); // Remove "Bearer " prefix
                String email = jwtUtil.extractEmail(token); // Extract email from token
                return ResponseEntity.ok("Token is valid for user: " + email);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Authorization header format");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RenteeDTO>> createRentee(@Valid @RequestBody RenteeDTO renteeDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ApiResponse<RenteeDTO> response = new ApiResponse<>(false, "Validation errors occurred", null);
            return ResponseEntity.badRequest().body(response);
        }

        if (renteeRepository.findByEmail(renteeDTO.getEmail()).isPresent()) {
            ApiResponse<RenteeDTO> response = new ApiResponse<>(false, "Email already registered as a rentee", null);
            return ResponseEntity.status(409).body(response);
        }

        if (renterRepository.findByEmail(renteeDTO.getEmail()).isPresent()) {
            ApiResponse<RenteeDTO> response = new ApiResponse<>(false, "Email already registered as a renter", null);
            return ResponseEntity.status(409).body(response);
        }

        // Save the renter
        RenteeDTO savedRenter = renteeService.saveUser(renteeDTO, bindingResult);
        ApiResponse<RenteeDTO> response = new ApiResponse<>(true, "Rentee registered successfully", savedRenter);
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        Optional<Rentee> renteeOpt = renteeRepository.findByEmail(email);
        if (!renteeOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "Email not registered."));
        }

        String resetToken = jwtUtil.generateToken(email);

        String resetLink = "http://localhost:3000/reset-password?token=" + resetToken;

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Password Reset Request");
            message.setText("Click the link to reset your password: " + resetLink);
            mailSender.send(message);

            return ResponseEntity.ok(Collections.singletonMap("message", "Password reset link sent to your email."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Error sending email."));
        }
    }


    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("password");

        try {
            String email = jwtUtil.extractEmail(token);

            Optional<Rentee> renteeOpt = renteeRepository.findByEmail(email);
            if (!renteeOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("message", "User not found."));
            }

            Rentee rentee = renteeOpt.get();
            rentee.setPassword(newPassword);
            renteeRepository.save(rentee);

            return ResponseEntity.ok(Collections.singletonMap("message", "Password has been reset successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("message", "Invalid or expired token."));
        }
    }
}
