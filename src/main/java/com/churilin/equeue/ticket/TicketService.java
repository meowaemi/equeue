package com.churilin.equeue.ticket;

import com.churilin.equeue.branch.Branch;
import com.churilin.equeue.branch.BranchRepository;
import com.churilin.equeue.catalog.ServiceType;
import com.churilin.equeue.catalog.ServiceTypeRepository;
import com.churilin.equeue.counter.Counter;
import com.churilin.equeue.counter.CounterRepository;
import com.churilin.equeue.ticket.dto.CreateTicketRequest;
import com.churilin.equeue.ticket.dto.TicketResponse;
import com.churilin.equeue.ticket.dto.UpdateTicketRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketService {

    private final TicketRepository ticketRepository;
    private final BranchRepository branchRepository;
    private final ServiceTypeRepository serviceTypeRepository;
    private final CounterRepository counterRepository;

    public TicketResponse create(CreateTicketRequest request) {
        Branch branch = branchRepository.findById(request.branchId())
                .orElseThrow(() -> new EntityNotFoundException("Branch " + request.branchId() + " not found"));
        ServiceType serviceType = serviceTypeRepository.findById(request.serviceTypeId())
                .filter(st -> st.getBranch().getId().equals(branch.getId()))
                .orElseThrow(() -> new EntityNotFoundException(
                        "Service type " + request.serviceTypeId() + " not found in branch " + branch.getId()));
        Ticket ticket = new Ticket(branch, serviceType, request.number(), request.priority());
        return TicketResponse.from(ticketRepository.save(ticket));
    }

    @Transactional(readOnly = true)
    public TicketResponse getById(Long id) {
        return TicketResponse.from(findTicket(id));
    }

    @Transactional(readOnly = true)
    public List<TicketResponse> getAll() {
        return ticketRepository.findAll().stream()
                .map(TicketResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TicketResponse> getAllByBranchAndStatus(Long branchId, TicketStatus status) {
        return ticketRepository.findAllByBranchIdAndStatus(branchId, status).stream()
                .map(TicketResponse::from)
                .toList();
    }

    public TicketResponse update(Long id, UpdateTicketRequest request) {
        Ticket ticket = findTicket(id);
        ticket.setStatus(request.status());
        ticket.setPriority(request.priority());
        ticket.setCounter(request.counterId() != null ? findCounter(ticket.getBranch().getId(), request.counterId()) : null);
        ticket.setCalledAt(request.calledAt());
        ticket.setStartedAt(request.startedAt());
        ticket.setServedAt(request.servedAt());
        return TicketResponse.from(ticket);
    }

    public void delete(Long id) {
        ticketRepository.delete(findTicket(id));
    }

    private Ticket findTicket(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket " + id + " not found"));
    }

    private Counter findCounter(Long branchId, Long counterId) {
        return counterRepository.findById(counterId)
                .filter(counter -> counter.getBranch().getId().equals(branchId))
                .orElseThrow(() -> new EntityNotFoundException(
                        "Counter " + counterId + " not found in branch " + branchId));
    }
}
