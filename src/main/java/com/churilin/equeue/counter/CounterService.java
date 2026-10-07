package com.churilin.equeue.counter;

import com.churilin.equeue.branch.Branch;
import com.churilin.equeue.branch.BranchRepository;
import com.churilin.equeue.catalog.ServiceType;
import com.churilin.equeue.catalog.ServiceTypeRepository;
import com.churilin.equeue.counter.dto.CounterResponse;
import com.churilin.equeue.counter.dto.CreateCounterRequest;
import com.churilin.equeue.counter.dto.UpdateCounterRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class CounterService {

    private final CounterRepository counterRepository;
    private final BranchRepository branchRepository;
    private final ServiceTypeRepository serviceTypeRepository;

    public CounterResponse create(Long branchId, CreateCounterRequest request) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new EntityNotFoundException("Branch " + branchId + " not found"));
        Set<ServiceType> serviceTypes = findServiceTypes(branchId, request.serviceTypeIds());
        Counter counter = new Counter(branch, request.number(), serviceTypes);
        return CounterResponse.from(counterRepository.save(counter));
    }

    @Transactional(readOnly = true)
    public CounterResponse getById(Long branchId, Long id) {
        return CounterResponse.from(findCounter(branchId, id));
    }

    @Transactional(readOnly = true)
    public List<CounterResponse> getAllByBranch(Long branchId) {
        return counterRepository.findAllByBranchId(branchId).stream()
                .map(CounterResponse::from)
                .toList();
    }

    public CounterResponse update(Long branchId, Long id, UpdateCounterRequest request) {
        Counter counter = findCounter(branchId, id);
        counter.setNumber(request.number());
        counter.setStatus(request.status());
        counter.setServiceTypes(findServiceTypes(branchId, request.serviceTypeIds()));
        return CounterResponse.from(counter);
    }

    public void delete(Long branchId, Long id) {
        counterRepository.delete(findCounter(branchId, id));
    }

    private Counter findCounter(Long branchId, Long id) {
        return counterRepository.findById(id)
                .filter(counter -> counter.getBranch().getId().equals(branchId))
                .orElseThrow(() -> new EntityNotFoundException(
                        "Counter " + id + " not found in branch " + branchId));
    }

    private Set<ServiceType> findServiceTypes(Long branchId, Set<Long> ids) {
        Set<ServiceType> serviceTypes = new HashSet<>(serviceTypeRepository.findAllById(ids));
        for (Long id : ids) {
            boolean found = serviceTypes.stream()
                    .anyMatch(st -> st.getId().equals(id) && st.getBranch().getId().equals(branchId));
            if (!found) {
                throw new EntityNotFoundException("Service type " + id + " not found in branch " + branchId);
            }
        }
        return serviceTypes;
    }
}
