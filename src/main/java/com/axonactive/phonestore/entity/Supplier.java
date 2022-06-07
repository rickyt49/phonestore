package com.axonactive.phonestore.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(nullable = false)
    private String fullName;

    @NotNull
    @Column(length = 10,nullable = false)
    @Size(min = 10, max = 10)
    private String phoneNumber;

    private String address;
}
