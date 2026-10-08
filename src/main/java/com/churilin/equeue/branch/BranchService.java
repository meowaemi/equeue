package com.churilin.equeue.branch;

import com.churilin.equeue.branch.dto.BranchResponse;
import com.churilin.equeue.branch.dto.CreateBranchRequest;
import com.churilin.equeue.branch.dto.UpdateBranchRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class BranchService {

    private final BranchRepository branchRepository;

    public BranchResponse create(CreateBranchRequest request) {
        Branch branch = new Branch(request.name(), request.address());
        return BranchResponse.from(branchRepository.save(branch));
    }

    @Transactional(readOnly = true)
    public BranchResponse getById(UUID id) {
        return BranchResponse.from(findBranch(id));
    }

    @Transactional(readOnly = true)
    public List<BranchResponse> getAll() {
        return branchRepository.findAll().stream()
                .map(BranchResponse::from)
                .toList();
    }

    public BranchResponse update(UUID id, UpdateBranchRequest request) {
        Branch branch = findBranch(id);
        branch.setName(request.name());
        branch.setAddress(request.address());
        return BranchResponse.from(branch);
    }

    public void delete(UUID id) {
        branchRepository.delete(findBranch(id));
    }

    private Branch findBranch(UUID id) {
        return branchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Branch " + id + " not found"));
    }
}
