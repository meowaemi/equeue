package com.churilin.equeue.counter.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Set;
import java.util.UUID;

public record CreateCounterRequest(
        @NotNull @Positive Integer number,
        @NotNull Set<@NotNull UUID> serviceTypeIds
) {
}
