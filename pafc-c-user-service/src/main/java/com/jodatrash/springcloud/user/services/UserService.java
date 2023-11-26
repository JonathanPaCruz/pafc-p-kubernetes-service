package com.jodatrash.springcloud.user.services;

import java.util.List;
import java.util.Optional;

import com.jodatrash.springcloud.user.entity.Users;

public interface UserService {
    List<Users> findAll();

    Optional<Users> byId(Long id);

    Users save(Users user);

    void delete(Long id);

}
