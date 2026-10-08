package com.churilin.equeue.catalog;

import com.churilin.equeue.branch.Branch;
import com.churilin.equeue.branch.BranchRepository;
import com.churilin.equeue.catalog.dto.CreateServiceTypeRequest;
import com.churilin.equeue.catalog.dto.ServiceTypeResponse;
import com.churilin.equeue.catalog.dto.UpdateServiceTypeRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ServiceTypeService {

    private final ServiceTypeRepository serviceTypeRepository;
    private final BranchRepository branchRepository;

    public ServiceTypeResponse create(UUID branchId, CreateServiceTypeRequest request) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new EntityNotFoundException("Branch " + branchId + " not found"));
        ServiceType serviceType = new ServiceType(branch, request.name(), request.prefix(), request.avgServiceMinutes());
        return ServiceTypeResponse.from(serviceTypeRepository.save(serviceType));
    }

    @Transactional(readOnly = true)
    public ServiceTypeResponse getById(UUID branchId, UUID id) {
        return ServiceTypeResponse.from(findServiceType(branchId, id));
    }

    @Transactional(readOnly = true)
    public List<ServiceTypeResponse> getAllByBranch(UUID branchId) {
        return serviceTypeRepository.findAllByBranchIdAndActiveTrue(branchId).stream()
                .map(ServiceTypeResponse::from)
                .toList();
    }

    public ServiceTypeResponse update(UUID branchId, UUID id, UpdateServiceTypeRequest request) {
        ServiceType serviceType = findServiceType(branchId, id);
        serviceType.setName(request.name());
        serviceType.setPrefix(request.prefix());
        serviceType.setAvgServiceMinutes(request.avgServiceMinutes());
        serviceType.setActive(request.active());
        return ServiceTypeResponse.from(serviceType);
    }

    public void delete(UUID branchId, UUID id) {
        serviceTypeRepository.delete(findServiceType(branchId, id));
    }

    private ServiceType findServiceType(UUID branchId, UUID id) {
        return serviceTypeRepository.findById(id)
                .filter(serviceType -> serviceType.getBranch().getId().equals(branchId))
                .orElseThrow(() -> new EntityNotFoundException(
                        "Service type " + id + " not found in branch " + branchId));
    }
}
