package com.churilin.equeue.ticket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateTicketRequest(
        @NotNull UUID branchId,
        @NotNull UUID serviceTypeId,
        @NotBlank @Size(max = 16) String number,
        @NotNull @PositiveOrZero Integer priority
) {
}
