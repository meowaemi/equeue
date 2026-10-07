package com.churilin.equeue.counter.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Set;

public record CreateCounterRequest(
        @NotNull @Positive Integer number,
        @NotNull Set<@NotNull Long> serviceTypeIds
) {
}
