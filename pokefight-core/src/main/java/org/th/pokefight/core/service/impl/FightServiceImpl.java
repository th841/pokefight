package org.th.pokefight.core.service.impl;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
import org.th.pokefight.core.service.PokemonService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FightServiceImpl implements FightService {

    private static final int FIRST_PAGE_INDEX = 0;
    private final FightRepository fightRepository;
    private final PokemonService pokemonService;

    @Autowired
    public FightServiceImpl(FightRepository fightRepository, PokemonService pokemonService) {
        this.fightRepository = fightRepository;
        this.pokemonService = pokemonService;
    }

    @Override
    public List<Fight> getLastFights(Integer amount) {
        Pageable pageable = PageRequest.of(FIRST_PAGE_INDEX, amount, Sort.by("timestamp")
                                                                         .descending());
        return fightRepository.findAll(pageable)
                              .toList();
    }

    @Override
    public Fight fight(List<Pokemon> pokemons) {
        String logMessage = pokemons.stream()
                                    .map(p -> String.format("<%s,%d>", p.getName(), p.getPower()))
                                    .collect(Collectors.joining(" vs "));

        log.info("Fight happening: {}", logMessage);

        Fight fight = doFight(pokemons);

        // persist fight data
        fightRepository.save(fight);

        // cleanup pokemons after fight
        pokemonService.removeFromCache(pokemons.stream()
                                               .map(Pokemon::getName)
                                               .toList());

        log.info("The winner is: <{}>", fight.getWinner());
        return fight;
    }

    private Fight doFight(List<Pokemon> pokemons) {
        FightBuilder fightBuilder = Fight.builder()
                                         .fighters(pokemons)
                                         .timestamp(Instant.now());

        Pokemon winner = pokemons.stream()
                                 .max(Comparator.comparingInt(Pokemon::getPower))
                                 .orElse(null);

        return fightBuilder.winner(winner)
                           .build();
    }

}
