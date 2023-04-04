package com.mindteck.backendapp.service;

import com.mindteck.backendapp.dto.UserDto;

import java.util.List;

public interface UserServiceI {

    public List<UserDto> getAllUsers ();
    public UserDto getUserById (Long id);
    public UserDto createUser (UserDto userDto);
    public UserDto updateUser(UserDto userDto);
    public String deleteUser(Long id);
}
