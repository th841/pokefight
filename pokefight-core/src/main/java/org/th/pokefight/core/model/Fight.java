package org.th.pokefight.core.model;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

/**
 * Stores 2 player fight result data, one winner, one loser
 * 
 * @author th
 */
@Data
@Document(collection = "fights")
@FieldNameConstants
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Fight {

    @Id
    private String id;
    @Indexed
    private Instant timestamp;
    private List<Pokemon> fighters;
    private Pokemon winner;

}
