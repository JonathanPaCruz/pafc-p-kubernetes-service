package com.jodatrash.springcloud.user.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jodatrash.springcloud.user.entity.Users;
import com.jodatrash.springcloud.user.services.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/private/v1/info/all")
    public List<Users> view() {
        return userService.findAll();
    }

    @GetMapping("/private/v1/info/user/{id}")
    public ResponseEntity<?> detailUsers(@PathVariable Long id) {
        Optional<Users> userOptional = userService.byId(id);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok().body(userOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/private/v1/create/user")
    // @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@Valid @RequestBody Users user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return getValidateResponse(bindingResult);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @PutMapping("/private/v1/edit/user/{id}")
    public ResponseEntity<?> edit(@Valid @RequestBody Users user, BindingResult bindingResult, @PathVariable Long id) {
        if (bindingResult.hasErrors()){
            return getValidateResponse(bindingResult);
        }
        Optional<Users> userOptional = userService.byId(id);
        if (userOptional.isPresent()) {
            Users userDb = userOptional.get();
            userDb.setName(user.getName());
            userDb.setEmail(user.getEmail());
            userDb.setPassword(user.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/private/v1/delete/user/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Users> userOptional = userService.byId(id);
        if (userOptional.isPresent()) {
            userService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.notFound().build();
    }

    private static ResponseEntity<Map<String, String>> getValidateResponse(BindingResult bindingResult) {
        Map<String, String> error = new HashMap<>();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            error.put(fieldError.getField(), "Campo " + fieldError.getField() +  " " + fieldError.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(error);
    }


}
