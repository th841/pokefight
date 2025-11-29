package org.th.pokefight.core.service;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.th.pokefight.core.model.Fight;
import org.th.pokefight.core.model.Pokemon;

import jakarta.validation.constraints.NotNull;

@Validated
public interface FightService {

    @NotNull
    List<Fight> getLastFights(@NotNull Integer amount);

    @NotNull
    Fight fight(@NotNull Pokemon pokemon0, @NotNull Pokemon pokemon1);
}
