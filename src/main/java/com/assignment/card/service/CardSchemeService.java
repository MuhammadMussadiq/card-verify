package com.assignment.card.service;

import com.assignment.card.dto.StatsDto;
import com.assignment.card.dto.VerifyResponseDto;

import java.io.IOException;

/**
 * Created by Mmussadiq on 4/5/2019.
 */
public interface CardSchemeService {

    VerifyResponseDto verify(String cardNumber) throws IOException;

    StatsDto getStats(Integer start, Integer limit);
}
