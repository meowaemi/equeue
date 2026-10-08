package com.churilin.equeue.counter.dto;

import com.churilin.equeue.counter.CounterStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Set;
import java.util.UUID;

public record UpdateCounterRequest(
        @NotNull @Positive Integer number,
        @NotNull CounterStatus status,
        @NotNull Set<@NotNull UUID> serviceTypeIds
) {
}
