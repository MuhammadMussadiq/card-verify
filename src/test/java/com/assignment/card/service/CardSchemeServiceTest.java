package com.assignment.card.service;

import com.assignment.card.dto.CardDto;
import com.assignment.card.dto.StatsDto;
import com.assignment.card.dto.VerifyResponseDto;
import com.assignment.card.entity.Card;
import com.assignment.card.exception.InvalidDataException;
import com.assignment.card.repository.CardSchemeRepository;
import com.assignment.card.utils.MockDataUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

/**
 * Created by Mmussadiq on 4/6/2019.
 */
@RunWith(MockitoJUnitRunner.class)
public class CardSchemeServiceTest {
    @Mock
    CardSchemeRepository cardSchemeRepository;

    @Mock
    ModelMapper modelMapper;

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    CardSchemeService service = new CardSchemeServiceImpl();

    @Before
    public void setup() {
        ReflectionTestUtils.setField(service, "binListUrl", MockDataUtils.LINK);
    }

    @Test
    public void verifyTest() throws IOException {
        String cardNumber =  MockDataUtils.CARD_NUMBER;
        String sampleResponse = MockDataUtils.getSampleResponse();

        when(modelMapper.map(any(Card.class), eq(CardDto.class))).thenReturn(MockDataUtils.getCardDto());
        when(restTemplate.getForEntity(Mockito.anyString(), eq(String.class)))
                .thenReturn(new ResponseEntity(sampleResponse, HttpStatus.OK));
        when(cardSchemeRepository.findBySchemeAndNumberAndTypeAndBank(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString(), Mockito.anyString())).thenReturn(null);

        VerifyResponseDto responseDto = service.verify(cardNumber);

        Assert.assertNotNull(responseDto);
        Assert.assertNotNull(responseDto.getPayload());
        Assert.assertEquals( MockDataUtils.SCHEME, responseDto.getPayload().getScheme());
        Assert.assertEquals(MockDataUtils.TYPE, responseDto.getPayload().getType());
        Assert.assertEquals(MockDataUtils.BANK, responseDto.getPayload().getBank());

    }

    @Test(expected = InvalidDataException.class)
    public void verifyMethodAgainstNullCardValueTest() throws IOException {
        String cardNumber = "null";
        service.verify(cardNumber);
    }

    @Test(expected = InvalidDataException.class)
    public void verifyMethodAgainstInvalidCardValueTest() throws IOException {
        String cardNumber = MockDataUtils.INVALID_CARD_NUMBER;
        service.verify(cardNumber);
    }

    @Test
    public void getStatsTest() throws IOException {
        Page<Card> cards = Mockito.mock(Page.class);
        when(cardSchemeRepository.findAll(any(Pageable.class))).thenReturn(cards);
        when(cards.getContent()).thenReturn(Arrays.asList(MockDataUtils.getCardEntity()));
        when(cards.getTotalElements()).thenReturn(1L);

        StatsDto statsDto = service.getStats(0, 5);
        Assert.assertNotNull(statsDto);
        Assert.assertTrue(statsDto.getPayload().size() > 0);
    }


}
