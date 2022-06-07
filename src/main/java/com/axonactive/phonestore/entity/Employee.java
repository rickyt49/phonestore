package com.axonactive.phonestore.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @Column(nullable = false)
    private String firstName;

    private String lastName;
    @NotNull
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    private String email;
    @NotNull
    @Column(nullable = false, length = 10)
    @Size(min = 10, max = 10)
    private String phoneNumber;
    private String address;
    @NotNull
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private EmployeeType employeeType;
    @NotNull
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private EmployeeStatus employeeStatus;

    private Integer managerId;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;
}
