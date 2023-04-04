package com.mindteck.backendapp.service;

import com.mindteck.backendapp.dto.DesignationDto;

import java.util.List;

public interface DesignationServiceI {

    public List<DesignationDto> getAllDesignations ();
    public DesignationDto getDsignationById (Long id);
    public DesignationDto createDesignation (DesignationDto designationDto);
    public DesignationDto updateDesignation(DesignationDto designationDto);
    public String deleteDesignation(Long id);
}
