package com.mindteck.backendapp.service;

import com.mindteck.backendapp.dto.DesignationDto;
import com.mindteck.backendapp.modal.Designation;
import com.mindteck.backendapp.modal.User;
import com.mindteck.backendapp.repository.DesignationRepository;
import com.mindteck.backendapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DesignationServiceImpl implements DesignationServiceI {

    private static final Logger logger = LoggerFactory.getLogger(DesignationServiceImpl.class);

    @Autowired
    DesignationRepository designationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;


    //get all
    @Override
    public List<DesignationDto> getAllDesignations () {

        List<Designation> allDesignations = designationRepository.findAll();

        return allDesignations.stream().map((designation) -> modelMapper.map(designation, DesignationDto.class))
                .collect(Collectors.toList());
    }


    //getbyid
    @Override
    public DesignationDto getDsignationById (Long id) {

        Optional<Designation> optionalById = designationRepository.findById(id);
        if (optionalById.isPresent()) {
            Designation designation = optionalById.get();

            //convert to dto
            DesignationDto designationDto = modelMapper.map(designation, DesignationDto.class);
            return designationDto;
        }
        return null;
    }



    //create
    @Override
    public DesignationDto createDesignation (DesignationDto designationDto) {
        //map to entity
        Designation designation = modelMapper.map(designationDto, Designation.class);

        //save
        Designation savedDesignation = designationRepository.save(designation);

        // Convertto dto

        DesignationDto savedDesignationDto = modelMapper.map(savedDesignation, DesignationDto.class);

        return savedDesignationDto;
    }


    @Transactional
    public DesignationDto updateDesignation(DesignationDto designationDto) {
        // Find the designation to update
        Optional<Designation> optionalDesignation = designationRepository.findById(designationDto.getId());

        if (optionalDesignation.isPresent()) {
            Designation _designation = optionalDesignation.get();
            // Update the designation
            _designation.setName(designationDto.getName());
            designationRepository.save(_designation);


            // Update the users with the new designation
            List<User> users = new ArrayList<>(_designation.getUsers());
            for (ListIterator<User> iterator = users.listIterator(); iterator.hasNext();) {
                User user = iterator.next();
                user.removeDesignation(_designation);
                user.addDesignation(_designation);
                try {
                    userRepository.save(user);
                    iterator.set(user);
                } catch (Exception e) {
                    throw new RuntimeException("Error updating user: " + e.getMessage());
                }
            }
            DesignationDto _designationDto = modelMapper.map(_designation, DesignationDto.class);
            return _designationDto;
        }

        return null;
    }



    @Override
    @Transactional
    public String deleteDesignation(Long id) {


        String response = "";


        Optional<Designation> designationOptional = designationRepository.findById(id);
        if (designationOptional.isPresent()) {
            Designation designation = designationOptional.get();

            // Remove the designation from all users and delete them
            List<User> users = designation.getUsers();
            ListIterator<User> userListIterator = users.listIterator();
            while (userListIterator.hasNext()) {
                User user = userListIterator.next();

                //if we want we can keep the user itself without designation for later use, right now deleting
                /*user.removeDesignation(designation);
                userRepository.save(user);
                userListIterator.remove();*/

                userRepository.delete(user);
                userListIterator.remove();

            }

            // Delete the designation itself
            try {
                designationRepository.delete(designation);
            } catch (Exception e) {
                logger.error("Error deleting designation with id " + designation.getId() + ": " + e.getMessage());
            }
        } else {
            logger.error("Designation with id " + id + " not found");
        }

        //find
        Optional<Designation> optionalById = designationRepository.findById(id);
        if (optionalById.isPresent()) {
            response = "DELETE FAILED";
            return response;
        } else {
            response = "DELETE SUCCESS";
            return response;
        }
    }








}
