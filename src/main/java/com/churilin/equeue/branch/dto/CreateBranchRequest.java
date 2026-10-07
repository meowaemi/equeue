package com.churilin.equeue.branch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateBranchRequest(
        @NotBlank @Size(max = 255) String name,
        @NotBlank @Size(max = 255) String address
) {
}
