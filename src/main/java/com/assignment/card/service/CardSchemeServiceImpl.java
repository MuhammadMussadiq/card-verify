package com.assignment.card.service;

import com.assignment.card.dto.CardDto;
import com.assignment.card.dto.StatsDto;
import com.assignment.card.dto.VerifyResponseDto;
import com.assignment.card.entity.Card;
import com.assignment.card.exception.InvalidDataException;
import com.assignment.card.exception.NotFoundException;
import com.assignment.card.repository.CardSchemeRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mmussadiq on 4/5/2019.
 */
@Service
public class CardSchemeServiceImpl implements CardSchemeService {

    @Autowired
    private CardSchemeRepository cardSchemeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${third.party.service}")
    private String binListUrl;

    /**
     * records entry for valid card only. If get 404 or 400 erro from third party then no hits will be recorded
     *
     * @param cardNumber
     * @return
     * @throws IOException
     */
    @Override
    public VerifyResponseDto verify(final String cardNumber) throws IOException {

        if (StringUtils.isEmpty(cardNumber) || !cardNumber.matches("\\d+")) {
            throw new InvalidDataException(HttpStatus.BAD_REQUEST.value()
                    , "Invalid card number. It should be not empty and must be in number format");
        }
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.getForEntity(binListUrl + cardNumber, String.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                throw new InvalidDataException(HttpStatus.BAD_REQUEST.value(), "Invalid request/card number");
            }
        }
        if (response == null) {
            throw new NotFoundException(HttpStatus.NOT_FOUND.value(),
                    "No card meta data found against card number: " + cardNumber);
        }
        Card card = convertToEntity(response.getBody());
        card.setNumber(cardNumber);
        saveCardInfo(card);

        return convertToDto(card);
    }

    /**
     * Get stats only for valid cards
     *
     * @param start
     * @param limit
     * @return
     */
    @Override
    public StatsDto getStats(Integer start, Integer limit) {
        if ((start == null || start < 0) || (limit == null || limit < 0)) {
            throw new InvalidDataException(HttpStatus.BAD_REQUEST.value(), "Start and Limit request parameter required");
        }
        Pageable pageable = PageRequest.of(start, limit);
        Page<Card> cards = cardSchemeRepository.findAll(pageable);
        Map<String, Integer> payload = new HashMap<>();
        cards.getContent().stream().forEach(card -> payload.put(card.getNumber(), card.getHits()));
        StatsDto statsDto = new StatsDto();
        statsDto.setSuccess(true);
        statsDto.setStart(start);
        statsDto.setLimit(limit);
        statsDto.setSize(cards.getTotalElements());
        statsDto.setPayload(payload);

        return statsDto;
    }

    /**
     * will convert json string to card entity
     *
     * @param body
     * @return
     * @throws IOException
     */
    private Card convertToEntity(String body) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(body);
        String scheme = jsonNode.get("scheme") != null ? jsonNode.get("scheme").asText("") : "";
        String type = jsonNode.get("type") != null ? jsonNode.get("type").asText("") : "";
        JsonNode bankObj = jsonNode.get("bank");
        String bankName = "";
        if (bankObj != null) {
            bankName = bankObj.get("name") != null ? bankObj.get("name").asText("") : "";
        }
        Card card = new Card(scheme, type, bankName);
        return card;
    }

    /**
     * save card entity in database
     *
     * @param card
     */
    private void saveCardInfo(Card card) {
        Card entity = cardSchemeRepository.findBySchemeAndNumberAndTypeAndBank(card.getScheme(),
                card.getNumber(), card.getType(), card.getBank());
        if (entity != null) {
            entity.setHits(entity.getHits() + 1);
            cardSchemeRepository.save(entity);
        } else {
            card.setHits(1);
            cardSchemeRepository.save(card);
        }
    }

    /**
     * entity to dto converter
     *
     * @param card
     * @return
     * @throws IOException
     */
    private VerifyResponseDto convertToDto(Card card) throws IOException {
        CardDto cardDto = modelMapper.map(card, CardDto.class);
        VerifyResponseDto responseDto = new VerifyResponseDto();
        responseDto.setSuccess(true);
        responseDto.setPayload(cardDto);
        return responseDto;
    }
}
