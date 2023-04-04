package com.mindteck.backendapp.dto;

public class DesignationDto {
    private Long id;
    private String name;


    public DesignationDto() {
    }


    public DesignationDto(Long id) {
        this.id = id;
    }

    public DesignationDto(String name) {
        this.name = name;
    }

    public DesignationDto(Long id, String name) {
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
}
