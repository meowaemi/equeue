package com.churilin.equeue.ticket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record CreateTicketRequest(
        @NotNull @Positive Long branchId,
        @NotNull @Positive Long serviceTypeId,
        @NotBlank @Size(max = 16) String number,
        @NotNull @PositiveOrZero Integer priority
) {
}
