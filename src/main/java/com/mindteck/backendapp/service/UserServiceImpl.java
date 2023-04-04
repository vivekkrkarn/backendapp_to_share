package com.mindteck.backendapp.service;

import com.mindteck.backendapp.dto.DesignationDto;
import com.mindteck.backendapp.dto.UserDto;
import com.mindteck.backendapp.modal.Designation;
import com.mindteck.backendapp.modal.User;
import com.mindteck.backendapp.repository.DesignationRepository;
import com.mindteck.backendapp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserServiceI{

    @Autowired
    UserRepository userRepository;

    @Autowired
    DesignationRepository designationRepository;

    @Autowired
    ModelMapper modelMapper;


    //get all
    @Override
    public List<UserDto> getAllUsers () {

        List<User> allUsers = userRepository.findAll();

        return allUsers.stream().map((user) -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }



    //getbyid
    @Override
    public UserDto getUserById (Long id) {

        Optional<User> optionalById = userRepository.findById(id);


        if (optionalById.isPresent()) {
            User user = optionalById.get();

            //convert to dto
            UserDto userDto = modelMapper.map(user, UserDto.class);
            return userDto;
        }
        return null;
    }


    //create
    @Override
    public UserDto createUser (UserDto userDto) {
        //map to entity
        User user = modelMapper.map(userDto, User.class);

        // Look up existing Designations by name
        List<Designation> designations = new ArrayList<>();
        for (DesignationDto designationDto : userDto.getDesignations()) {
            Designation existingDesignation = designationRepository.findByName(designationDto.getName());
            if (existingDesignation != null) {
                designations.add(existingDesignation);
            } else {
                Designation newDesignation = modelMapper.map(designationDto, Designation.class);
                designations.add(newDesignation);
            }
        }

        // Set the Designations on the User entity
        user.setDesignations(designations);

        //save
        User savedUser = userRepository.save(user);

        // Convertto dto
        UserDto savedUserDto = modelMapper.map(savedUser, UserDto.class);

        return savedUserDto;
    }



    //update
    @Override
    public UserDto updateUser(UserDto userDto) {

        //find
        Optional<User> optionalById = userRepository.findById(userDto.getId());

        if (optionalById.isPresent()) {
            User userFromDb = optionalById.get();

            //update fields
            if (!userDto.getName().isEmpty()) {
                userFromDb.setName(userDto.getName());
            }
            if (!userDto.getUsername().isEmpty()) {
                userFromDb.setUsername(userDto.getUsername());
            }
            if (!userDto.getPassword().isEmpty()) {
                userFromDb.setPassword(userDto.getPassword());
            }
            if (!userDto.getDesignations().isEmpty()) {


                // Get the existing Designation entities from the database
                List<Designation> existingDesignations = designationRepository.findByNameIn(
                        userDto.getDesignations().stream()
                                .map(DesignationDto::getName)
                                .collect(Collectors.toList()));

                // Remove any Designations from the user's existing designations collection
                // that are not present in the updated userDto's designations collection
                List<Designation> removedDesignations = new ArrayList<>();
                for (Designation designation : userFromDb.getDesignations()) {
                    if (!existingDesignations.contains(designation)) {
                        designation.removeUser(userFromDb);
                        removedDesignations.add(designation);
                    }
                }
                userFromDb.getDesignations().removeAll(removedDesignations);

                // Add any new Designations to the user's existing designations collection
                for (DesignationDto designationDTO : userDto.getDesignations()) {
                    Designation existingDesignation = existingDesignations.stream()
                            .filter(d -> d.getName().equals(designationDTO.getName()))
                            .findFirst()
                            .orElse(null);
                    if (existingDesignation != null) {
                        userFromDb.addDesignation(existingDesignation);
                    } else {
                        Designation newDesignation = modelMapper.map(designationDTO, Designation.class);
                        userFromDb.addDesignation(newDesignation);
                    }
                }
            }

            //update to db
            userRepository.save(userFromDb);

            UserDto userDtoUpdated = modelMapper.map(userFromDb, UserDto.class);
            return userDtoUpdated;
        }

        return null;
    }


    //delete
    @Override
    public String deleteUser(Long id) {
        String response = "";

        //delete
        userRepository.deleteById(id);

        // verify
        Optional<User> optionalById = userRepository.findById(id);
        if (optionalById.isPresent()) {
            response = "DELETE FAILED";
            return response;
        } else {
            response = "DELETE SUCCESS";
            return response;
        }
    }

}
