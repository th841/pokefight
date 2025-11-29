package org.th.pokefight.core.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.th.pokefight.core.model.Fight;

public interface FightRepository extends MongoRepository<Fight, String> {
}
