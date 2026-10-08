package com.churilin.equeue.ticket.dto;

import com.churilin.equeue.ticket.Ticket;
import com.churilin.equeue.ticket.TicketStatus;

import java.time.Instant;
import java.util.UUID;

public record TicketResponse(
        UUID id,
        UUID branchId,
        UUID serviceTypeId,
        String number,
        TicketStatus status,
        Integer priority,
        UUID counterId,
        Instant createdAt,
        Instant calledAt,
        Instant startedAt,
        Instant servedAt,
        Long version
) {
    public static TicketResponse from(Ticket ticket) {
        return new TicketResponse(
                ticket.getId(),
                ticket.getBranch().getId(),
                ticket.getServiceType().getId(),
                ticket.getNumber(),
                ticket.getStatus(),
                ticket.getPriority(),
                ticket.getCounter() != null ? ticket.getCounter().getId() : null,
                ticket.getCreatedAt(),
                ticket.getCalledAt(),
                ticket.getStartedAt(),
                ticket.getServedAt(),
                ticket.getVersion()
        );
    }
}
