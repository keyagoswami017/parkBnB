package edu.neu.csye6200.parkingapp.service;

import edu.neu.csye6200.parkingapp.dto.CardDTO;
import edu.neu.csye6200.parkingapp.model.Card;
import edu.neu.csye6200.parkingapp.model.Rentee;
import edu.neu.csye6200.parkingapp.repository.CardRepository;
import edu.neu.csye6200.parkingapp.repository.PaymentRepository;
import edu.neu.csye6200.parkingapp.repository.RenteeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardService {
    @Autowired
    CardRepository cardRepository;

    @Autowired
    RenteeRepository renteeRepository;

    @Autowired
    PaymentRepository paymentRepository;

    public CardDTO addCard(CardDTO cardDTO) {
        Card card = new Card();
        card.setLast4(cardDTO.getLast4());
        card.setCardType(cardDTO.getCardType());
        card.setStripeCardId(cardDTO.getStripeCardId());
        card.setExpiryDate(cardDTO.getExpiryDate());
        card.setCardHolderName(cardDTO.getCardHolderName());

        Rentee rentee = renteeRepository.findById(cardDTO.getRenteeId())
                .orElseThrow(() -> new IllegalArgumentException("Rentee not found"));

        card.setRentee(rentee);
        card = cardRepository.save(card);
        return convertToDTO(card);
    }

    public List<CardDTO> getCardsByRenteeId(Long renteeId) {
        List<Card> cards = cardRepository.findByRenteeId(renteeId);
        return cards.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private CardDTO convertToDTO(Card card) {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setId(card.getId());
        cardDTO.setLast4(card.getLast4());
        cardDTO.setCardType(card.getCardType());
        cardDTO.setStripeCardId(card.getStripeCardId());
        cardDTO.setExpiryDate(card.getExpiryDate());
        cardDTO.setRenteeId(card.getRentee().getId());
        cardDTO.setCardHolderName(card.getCardHolderName());
        return cardDTO;
    }
    public Card mapToCardEntity(CardDTO cardDTO) {
        Card card = new Card();
        card.setLast4(cardDTO.getLast4());
        card.setCardType(cardDTO.getCardType());
        card.setStripeCardId(cardDTO.getStripeCardId());
        card.setExpiryDate(cardDTO.getExpiryDate());

        Rentee rentee = renteeRepository.findById(cardDTO.getRenteeId())
                .orElseThrow(() -> new IllegalArgumentException("Rentee not found"));
        card.setRentee(rentee);

        return card;
    }

    public void deleteCard(Long cardId) {
        if (paymentRepository.existsByCardId(cardId)) {
            throw new IllegalStateException("This card is associated with existing payments. Please remove payments first.");
        }
        if (cardRepository.existsById(cardId)) {
            cardRepository.deleteById(cardId);
        } else {
            throw new RuntimeException("Card not found");
        }
    }
}
