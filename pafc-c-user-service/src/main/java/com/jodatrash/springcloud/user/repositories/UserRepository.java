package com.jodatrash.springcloud.user.repositories;

import org.springframework.data.repository.CrudRepository;

import com.jodatrash.springcloud.user.entity.Users;

public interface UserRepository extends CrudRepository<Users, Long> {

}
