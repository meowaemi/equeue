package com.churilin.equeue.counter;

import com.churilin.equeue.counter.dto.CounterResponse;
import com.churilin.equeue.counter.dto.CreateCounterRequest;
import com.churilin.equeue.counter.dto.UpdateCounterRequest;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/branches/{branchId}/counters")
@RequiredArgsConstructor
public class CounterController {

    private final CounterService counterService;

    @GetMapping
    public List<CounterResponse> getAll(@PathVariable UUID branchId) {
        return counterService.getAllByBranch(branchId);
    }

    @GetMapping("/{id}")
    public CounterResponse getById(@PathVariable UUID branchId, @PathVariable UUID id) {
        return counterService.getById(branchId, id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CounterResponse create(@PathVariable UUID branchId,
                                  @Valid @RequestBody CreateCounterRequest request) {
        return counterService.create(branchId, request);
    }

    @PutMapping("/{id}")
    public CounterResponse update(@PathVariable UUID branchId, @PathVariable UUID id,
                                  @Valid @RequestBody UpdateCounterRequest request) {
        return counterService.update(branchId, id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID branchId, @PathVariable UUID id) {
        counterService.delete(branchId, id);
    }
}
