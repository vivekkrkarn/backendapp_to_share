package com.mindteck.backendapp.modal;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "designation")

public class Designation implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	@Column(length = 20)
	private String name;

	@ManyToMany(mappedBy = "designations")
	private List<User> users = new ArrayList<>();

	public Designation() {
		// Initialize the users collection with an empty list
		this.users = new ArrayList<>();
	}


	public void addUser(User user) {
		if (!users.contains(user)) {
			users.add(user);
			user.addDesignation(this);
		}
	}

	public void removeUser(User user) {
		if (users != null && users.contains(user)) {
			users.remove(user);
			user.removeDesignation(this);
		}
	}



	public Designation(String name) {
		this.name = name;
	}


	public Designation(Long id, String name) {
		this.id = id;
		this.name = name;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<User> getUsers() {
		return users;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.name);
	}
}