package org.th.pokefight.core.service.impl;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.th.pokefight.core.model.Fight;
import org.th.pokefight.core.model.Fight.FightBuilder;
import org.th.pokefight.core.model.Pokemon;
import org.th.pokefight.core.repository.FightRepository;
import org.th.pokefight.core.service.FightService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FightServiceImpl implements FightService {

    private static final int FIRST_PAGE_INDEX = 0;
    private final FightRepository fightRepository;

    @Autowired
    public FightServiceImpl(FightRepository fightRepository) {
        this.fightRepository = fightRepository;
    }

    @Override
    public List<Fight> getLastFights(Integer amount) {
        Pageable pageable = PageRequest.of(FIRST_PAGE_INDEX, amount, Sort.by("timestamp")
                                                                         .descending());
        return fightRepository.findAll(pageable)
                              .toList();
    }

    @Override
    public Fight fight(Pokemon pokemon0, Pokemon pokemon1) {
        log.info("Fight happening: pokemon 1: <{}>, pokemon 2:<{}>", pokemon0.getName(), pokemon1.getName());
        Fight fight = doFight(pokemon0, pokemon1);
        fightRepository.save(fight);
        log.info("The winner is: <{}>", fight.getWinner());
        return fight;
    }

    private Fight doFight(Pokemon pokemon0, Pokemon pokemon1) {
        FightBuilder fightBuilder = Fight.builder()
                                         .fighters(List.of(pokemon0, pokemon1))
                                         .timestamp(Instant.now());

        if (pokemon0.getPower() > pokemon1.getPower()) {
            fightBuilder.winner(pokemon0);
        } else if (pokemon1.getPower() > pokemon0.getPower()) {
            fightBuilder.winner(pokemon1);
        }

        Fight fight = fightBuilder.build();
        return fight;
    }

}
