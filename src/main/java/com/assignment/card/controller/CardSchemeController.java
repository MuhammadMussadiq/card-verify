package com.assignment.card.controller;

import com.assignment.card.dto.StatsDto;
import com.assignment.card.dto.VerifyResponseDto;
import com.assignment.card.service.CardSchemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by Mmussadiq on 4/5/2019.
 */
@RestController
@RequestMapping(value = "card-scheme")
public class CardSchemeController {

    @Autowired
    private CardSchemeService cardSchemeService;

    @GetMapping(value = "verify/{cardNumber}")
    public VerifyResponseDto verify(@PathVariable("cardNumber") String cardNumber) throws IOException {
        return cardSchemeService.verify(cardNumber);
    }

    @GetMapping(value = "stats")
    public StatsDto getStats(@RequestParam(value = "start", required = false) Integer start,
                             @RequestParam(value = "limit", required = false) Integer limit) throws IOException {
        return cardSchemeService.getStats(start, limit);
    }
}
