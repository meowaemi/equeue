package com.churilin.equeue.ticket;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findAllByBranchIdAndStatus(Long branchId, TicketStatus status);
}
