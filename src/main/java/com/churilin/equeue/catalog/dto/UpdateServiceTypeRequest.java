package com.churilin.equeue.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UpdateServiceTypeRequest(
        @NotBlank @Size(max = 255) String name,
        @NotBlank @Size(max = 8) String prefix,
        @NotNull @Positive Integer avgServiceMinutes,
        @NotNull Boolean active
) {
}
