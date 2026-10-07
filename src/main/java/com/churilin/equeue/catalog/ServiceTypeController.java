package com.churilin.equeue.catalog;

import com.churilin.equeue.catalog.dto.CreateServiceTypeRequest;
import com.churilin.equeue.catalog.dto.ServiceTypeResponse;
import com.churilin.equeue.catalog.dto.UpdateServiceTypeRequest;
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

@RestController
@RequestMapping("/api/branches/{branchId}/services")
@RequiredArgsConstructor
public class ServiceTypeController {

    private final ServiceTypeService serviceTypeService;

    @GetMapping
    public List<ServiceTypeResponse> getAll(@PathVariable Long branchId) {
        return serviceTypeService.getAllByBranch(branchId);
    }

    @GetMapping("/{id}")
    public ServiceTypeResponse getById(@PathVariable Long branchId, @PathVariable Long id) {
        return serviceTypeService.getById(branchId, id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceTypeResponse create(@PathVariable Long branchId,
                                      @Valid @RequestBody CreateServiceTypeRequest request) {
        return serviceTypeService.create(branchId, request);
    }

    @PutMapping("/{id}")
    public ServiceTypeResponse update(@PathVariable Long branchId, @PathVariable Long id,
                                      @Valid @RequestBody UpdateServiceTypeRequest request) {
        return serviceTypeService.update(branchId, id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long branchId, @PathVariable Long id) {
        serviceTypeService.delete(branchId, id);
    }
}
