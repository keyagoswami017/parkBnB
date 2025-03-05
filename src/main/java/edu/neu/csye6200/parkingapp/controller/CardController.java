package edu.neu.csye6200.parkingapp.controller;

import edu.neu.csye6200.parkingapp.dto.CardDTO;
import edu.neu.csye6200.parkingapp.service.CardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {

@Autowired
CardService cardService;

    @PostMapping
    public ResponseEntity<CardDTO> addCard(@Valid @RequestBody CardDTO cardDTO) {
        final CardDTO createdCard = cardService.addCard(cardDTO);
        return new ResponseEntity<>(createdCard, HttpStatus.CREATED);
    }

    @GetMapping("/rentee/{renteeId}")
    public ResponseEntity<List<CardDTO>>  getCardsByRenteeId(@PathVariable Long renteeId) {
        final List<CardDTO> cardsByRenteeId = cardService.getCardsByRenteeId(renteeId);
        return ResponseEntity.ok(cardsByRenteeId);
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<String> deleteCard(@PathVariable Long cardId) {
        try {
            cardService.deleteCard(cardId);
            return ResponseEntity.ok().build();  // Successfully deleted
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Card cannot be deleted because it is referenced in payments.");
        }
    }
}
