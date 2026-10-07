package com.churilin.equeue.branch.dto;

import com.churilin.equeue.branch.Branch;

import java.time.Instant;

public record BranchResponse(
        Long id,
        String name,
        String address,
        Instant createdAt
) {
    public static BranchResponse from(Branch branch) {
        return new BranchResponse(
                branch.getId(),
                branch.getName(),
                branch.getAddress(),
                branch.getCreatedAt()
        );
    }
}
