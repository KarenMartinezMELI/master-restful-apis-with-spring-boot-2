package com.stacksimplify.restservices.controllers;

import com.stacksimplify.restservices.dtos.user.UserV1DTO;
import com.stacksimplify.restservices.dtos.user.UserV2DTO;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.services.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.Min;

@RestController
@RequestMapping("/versioning/params/users")
public class UserRequestParameterVersioningController {

    private final IUserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserRequestParameterVersioningController(IUserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @GetMapping(value = "/{id}", params = "version=1")
    public ResponseEntity<UserV1DTO> getUserByIdV1(@Min(1) @PathVariable Long id) {
        try {
            return new ResponseEntity<>(modelMapper.map(userService.getUserById(id),UserV1DTO.class), HttpStatus.OK);
        }catch (UserNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @GetMapping(value = "/{id}", params = "version=2")
    public ResponseEntity<UserV2DTO> getUserByIdV2(@Min(1) @PathVariable Long id) {
        try {
            return new ResponseEntity<>(modelMapper.map(userService.getUserById(id),UserV2DTO.class), HttpStatus.OK);
        }catch (UserNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }
}
