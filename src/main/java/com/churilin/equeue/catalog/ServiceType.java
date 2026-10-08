package com.churilin.equeue.catalog;

import com.churilin.equeue.branch.Branch;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "service_type")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String prefix;

    @Column(name = "avg_service_minutes", nullable = false)
    private Integer avgServiceMinutes;

    @Column(nullable = false)
    private boolean active;

    public ServiceType(Branch branch, String name, String prefix, Integer avgServiceMinutes) {
        this.branch = branch;
        this.name = name;
        this.prefix = prefix;
        this.avgServiceMinutes = avgServiceMinutes;
        this.active = true;
    }
}
