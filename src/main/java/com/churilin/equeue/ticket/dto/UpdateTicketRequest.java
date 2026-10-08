package com.churilin.equeue.ticket.dto;

import com.churilin.equeue.ticket.TicketStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.Instant;
import java.util.UUID;

public record UpdateTicketRequest(
        @NotNull TicketStatus status,
        @NotNull @PositiveOrZero Integer priority,
        UUID counterId,
        Instant calledAt,
        Instant startedAt,
        Instant servedAt
) {
}
