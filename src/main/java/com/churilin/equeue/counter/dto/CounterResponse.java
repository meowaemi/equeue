package com.churilin.equeue.counter.dto;

import com.churilin.equeue.catalog.ServiceType;
import com.churilin.equeue.counter.Counter;
import com.churilin.equeue.counter.CounterStatus;

import java.util.List;

public record CounterResponse(
        Long id,
        Long branchId,
        Integer number,
        CounterStatus status,
        List<Long> serviceTypeIds
) {
    public static CounterResponse from(Counter counter) {
        return new CounterResponse(
                counter.getId(),
                counter.getBranch().getId(),
                counter.getNumber(),
                counter.getStatus(),
                counter.getServiceTypes().stream()
                        .map(ServiceType::getId)
                        .sorted()
                        .toList()
        );
    }
}
