package com.assignment.card.repository;

import com.assignment.card.entity.Card;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Mmussadiq on 4/5/2019.
 */
@Repository
public interface CardSchemeRepository extends PagingAndSortingRepository<Card, Long> {

    Card findBySchemeAndNumberAndTypeAndBank(String scheme, String cardNumber, String type, String bank);
}
