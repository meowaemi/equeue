package com.churilin.equeue.ticket;

import com.churilin.equeue.ticket.dto.CreateTicketRequest;
import com.churilin.equeue.ticket.dto.TicketResponse;
import com.churilin.equeue.ticket.dto.UpdateTicketRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @GetMapping
    public List<TicketResponse> getAll(@RequestParam(required = false) UUID branchId,
                                       @RequestParam(required = false) TicketStatus status) {
        if (branchId != null && status != null) {
            return ticketService.getAllByBranchAndStatus(branchId, status);
        }
        return ticketService.getAll();
    }

    @GetMapping("/{id}")
    public TicketResponse getById(@PathVariable UUID id) {
        return ticketService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TicketResponse create(@Valid @RequestBody CreateTicketRequest request) {
        return ticketService.create(request);
    }

    @PutMapping("/{id}")
    public TicketResponse update(@PathVariable UUID id, @Valid @RequestBody UpdateTicketRequest request) {
        return ticketService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        ticketService.delete(id);
    }
}
