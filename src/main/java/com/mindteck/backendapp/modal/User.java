package com.mindteck.backendapp.modal;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(	name = "users",
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "username"),
			@UniqueConstraint(columnNames = "name")
		})
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 20)
	private String username;

	@NotBlank
	@Size(max = 50)
	private String password;


	@NotBlank
	@Size(max = 50)
	private String name;


	/*@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_designation",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "designation_id"))*/
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "designation_id", referencedColumnName = "id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Designation> designations = new ArrayList<>();

	public void addDesignation(Designation designation) {
		if (!designations.contains(designation)) {
			designations.add(designation);
			designation.getUsers().add(this);
		}
	}

	public void removeDesignation(Designation designation) {
		if (designations != null && designations.contains(designation)) {
			designations.remove(designation);
			designation.getUsers().remove(this);
		}
	}

	public User() {
	}

	public User(String username, String password, String name, List<Designation> designations) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.designations = designations;
	}

	public User(Long id, String username, String password, String name, List<Designation> designations) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.designations = designations;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Designation> getDesignations() {
		return designations;
	}

	public void setDesignations(List<Designation> designations) {
		this.designations = designations;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.name);
	}
}
