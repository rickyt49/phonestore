package com.axonactive.phonestore.security.entity;

import com.axonactive.phonestore.entity.Employee;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String username;

    @JsonIgnore
    private String password;

    private Boolean active;

    @OneToMany(mappedBy = "users")
    private List<UserRoleAssignment> roles = new ArrayList<>();

    @ManyToOne
    @JoinColumn
    private Employee employee;


}
