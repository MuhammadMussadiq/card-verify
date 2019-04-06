package com.assignment.card.utils;

import com.assignment.card.dto.CardDto;
import com.assignment.card.dto.StatsDto;
import com.assignment.card.dto.VerifyResponseDto;
import com.assignment.card.entity.Card;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mmussadiq on 4/6/2019.
 */
public class MockDataUtils {
    public static final String CARD_NUMBER = "45717360";
    public static final String SCHEME = "visa";
    public static final java.lang.Object LINK = "https://lookup.binlist.net/";
    public static final String TYPE = "debit";
    public static final String BANK = "Jyske Bank";
    public static final String INVALID_CARD_NUMBER = "678234ghf";
    public static final Integer COUNT_AGAINST_CARD_NUMBER = 5;

    public static CardDto getCardDto() {
        return new CardDto(SCHEME, TYPE, BANK);
    }

    public static Card getCardEntity() {
        Card card = new Card(SCHEME, TYPE, BANK);
        card.setHits(4);
        card.setNumber(CARD_NUMBER);
        return card;
    }

    public static StatsDto getStatDto() {
        StatsDto statsDto = new StatsDto();
        statsDto.setStart(0);
        statsDto.setSuccess(true);
        statsDto.setLimit(10);
        statsDto.setSize(13L);

        Map<String, Integer> map = new HashMap<>();
        map.put(CARD_NUMBER, COUNT_AGAINST_CARD_NUMBER);

        statsDto.setPayload(map);
        return statsDto;
    }

    public static String getSampleResponse() {
        return "{\n" +
                "    \"number\": {\n" +
                "        \"length\": 16,\n" +
                "        \"luhn\": true\n" +
                "    },\n" +
                "    \"scheme\": \"visa\",\n" +
                "    \"type\": \"debit\",\n" +
                "    \"brand\": \"Visa/Dankort\",\n" +
                "    \"prepaid\": false,\n" +
                "    \"country\": {\n" +
                "        \"numeric\": \"208\",\n" +
                "        \"alpha2\": \"DK\",\n" +
                "        \"name\": \"Denmark\",\n" +
                "        \"emoji\": \"\uD83C\uDDE9\uD83C\uDDF0\",\n" +
                "        \"currency\": \"DKK\",\n" +
                "        \"latitude\": 56,\n" +
                "        \"longitude\": 10\n" +
                "    },\n" +
                "    \"bank\": {\n" +
                "        \"name\": \"Jyske Bank\",\n" +
                "        \"url\": \"www.jyskebank.dk\",\n" +
                "        \"phone\": \"+4589893300\",\n" +
                "        \"city\": \"Hjørring\"\n" +
                "    }\n" +
                "}";
    }


    public static VerifyResponseDto getVerifyResponseDto() {
        VerifyResponseDto responseDto = new VerifyResponseDto();
        responseDto.setPayload(getCardDto());
        responseDto.setSuccess(true);
        return responseDto;
    }
}
