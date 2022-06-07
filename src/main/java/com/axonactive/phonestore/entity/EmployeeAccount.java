package com.axonactive.phonestore.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EmployeeAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(unique = true, nullable = false)
    private String username;
    @NotNull
    @Column(nullable = false)
    private String password;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
