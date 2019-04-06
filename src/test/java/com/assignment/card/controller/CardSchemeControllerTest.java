package com.assignment.card.controller;

import com.assignment.card.dto.StatsDto;
import com.assignment.card.dto.VerifyResponseDto;
import com.assignment.card.service.CardSchemeService;
import com.assignment.card.utils.MockDataUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Mmussadiq on 4/6/2019.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CardSchemeController.class)
public class CardSchemeControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CardSchemeService cardSchemeService;

    @Test
    public void verifyCardNumberEndpointTest()
            throws Exception {

        VerifyResponseDto responseDto = MockDataUtils.getVerifyResponseDto();

        given(cardSchemeService.verify(MockDataUtils.CARD_NUMBER)).willReturn(responseDto);

        mvc.perform( MockMvcRequestBuilders
                .get("/card-scheme/verify/{cardNumber}", MockDataUtils.CARD_NUMBER)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.scheme").value(MockDataUtils.SCHEME));
    }


    @Test
    public void getStatsEndpointTest()
            throws Exception {

        StatsDto responseDto = MockDataUtils.getStatDto();

        given(cardSchemeService.getStats(anyInt(), anyInt())).willReturn(responseDto);

        mvc.perform( MockMvcRequestBuilders
                .get("/card-scheme/stats?start=0&limit=10")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload."+MockDataUtils.CARD_NUMBER).value(MockDataUtils.COUNT_AGAINST_CARD_NUMBER));
    }

}
