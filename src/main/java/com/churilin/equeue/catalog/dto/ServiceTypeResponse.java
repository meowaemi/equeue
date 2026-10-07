package com.churilin.equeue.catalog.dto;

import com.churilin.equeue.catalog.ServiceType;

public record ServiceTypeResponse(
        Long id,
        Long branchId,
        String name,
        String prefix,
        Integer avgServiceMinutes,
        boolean active
) {
    public static ServiceTypeResponse from(ServiceType serviceType) {
        return new ServiceTypeResponse(
                serviceType.getId(),
                serviceType.getBranch().getId(),
                serviceType.getName(),
                serviceType.getPrefix(),
                serviceType.getAvgServiceMinutes(),
                serviceType.isActive()
        );
    }
}
