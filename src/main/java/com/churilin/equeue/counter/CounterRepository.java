package com.churilin.equeue.counter;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CounterRepository extends JpaRepository<Counter, Long> {

    List<Counter> findAllByBranchId(Long branchId);
}
