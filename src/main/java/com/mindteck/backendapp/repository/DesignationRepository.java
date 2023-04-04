package com.mindteck.backendapp.repository;

import com.mindteck.backendapp.modal.Designation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignationRepository extends JpaRepository<Designation, Long> {

    public Designation findByName (String name);
    List<Designation> findByNameIn(List<String> names);
}
