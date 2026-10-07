package com.churilin.equeue.branch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateBranchRequest(
        @NotBlank @Size(max = 255) String name,
        @NotBlank @Size(max = 255) String address
) {
}
