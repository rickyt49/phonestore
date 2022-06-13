package com.axonactive.phonestore.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Store {
    @Transient
    private final String PHONE_NUMBER_REGEX = "(^$|[0-9]{10})";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(length = 10)
    @Size(min = 10, max = 10)
    @Pattern(regexp = PHONE_NUMBER_REGEX)
    private String phoneNumber;

    @NotNull
    @Column(nullable = false)
    private String address;

    @NotNull
    @Column(nullable = false)
    private String city;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;
}
