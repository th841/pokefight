package org.th.pokefight.api.error.dto;

import java.time.Instant;

/**
 * Indicating a wrong request, problem in request call
 * 
 * @author th
 */
public record ClientErrorDTO(String message, int status, Instant timestamp) {
}
