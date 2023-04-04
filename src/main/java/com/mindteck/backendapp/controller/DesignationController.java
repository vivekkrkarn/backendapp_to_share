package com.mindteck.backendapp.controller;

import com.mindteck.backendapp.dto.DesignationDto;
import com.mindteck.backendapp.service.DesignationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/designation")
public class DesignationController {

    private static final Logger logger = LoggerFactory.getLogger(DesignationController.class);

    @Autowired
    DesignationServiceImpl designationService;

    @GetMapping("/getall")
    public ResponseEntity<?> getAllDesignations() {

        try {
            List<DesignationDto> allDesignations = designationService.getAllDesignations();
            if (allDesignations.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(allDesignations, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("exception in DesignationController, getAllDesignations() method, error message is = {} ", e.getMessage());
        }
        return null;
    }



    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") long id) {

        try {
            DesignationDto designationById = designationService.getDsignationById(id);
            if (designationById == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(designationById, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("exception in DesignationController, getById() method, error message is = {} ", e.getMessage());
        }
        return null;
    }


    @PostMapping("/create")
    public ResponseEntity<?> createDesignation(@RequestBody DesignationDto designationDto) {

        try {
            DesignationDto designation = designationService.createDesignation(designationDto);

            if (designation == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(designation, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("exception in DesignationController, createDesignation() method, error message is = {} ", e.getMessage());
        }
        return null;
    }



    @PutMapping("/update")
    public ResponseEntity<?> updateDesignation(@RequestBody DesignationDto designationDto) {
        try {
            DesignationDto _designationDto = designationService.updateDesignation(designationDto);

            if (_designationDto == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(_designationDto, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("exception in UserController, updateDesignation() method, error message is = {} ", e.getMessage());
        }
        return null;
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDesignation(@PathVariable("id") long id) {
        String result = designationService.deleteDesignation(id);

        try {
            if (result == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else if (result.equals("DELETE FAILED")) {
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
            else if (result.equals("DELETE SUCCESS")){
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("exception in UserController, deleteDesignation() method, error message is = {} ", e.getMessage());
        }
        return null;
    }

}
