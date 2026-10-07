package com.churilin.equeue.counter;

import com.churilin.equeue.branch.Branch;
import com.churilin.equeue.catalog.ServiceType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "counter")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Counter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @Column(nullable = false)
    private Integer number;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CounterStatus status;

    @ManyToMany
    @JoinTable(
            name = "counter_service_type",
            joinColumns = @JoinColumn(name = "counter_id"),
            inverseJoinColumns = @JoinColumn(name = "service_type_id")
    )
    private Set<ServiceType> serviceTypes = new HashSet<>();

    public Counter(Branch branch, Integer number, Set<ServiceType> serviceTypes) {
        this.branch = branch;
        this.number = number;
        this.status = CounterStatus.CLOSED;
        this.serviceTypes = new HashSet<>(serviceTypes);
    }
}
