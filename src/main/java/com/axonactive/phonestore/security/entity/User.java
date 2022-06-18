package com.axonactive.phonestore.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="users")
@NoArgsConstructor
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public List<UserRoleAssignment> getRoles() {
		return roles;
	}

	public void setRoles(List<UserRoleAssignment> roles) {
		this.roles = roles;
	}

	

}
