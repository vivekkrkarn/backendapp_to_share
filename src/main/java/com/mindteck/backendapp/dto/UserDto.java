package com.mindteck.backendapp.dto;

import java.util.List;

public class UserDto {


    private Long id;
    private String username;
    private String password;
    private String name;
    private List<DesignationDto> designations;



    public UserDto() {
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

    public List<DesignationDto> getDesignations() {
        return designations;
    }

    public void setDesignations(List<DesignationDto> designations) {
        this.designations = designations;
    }
}
