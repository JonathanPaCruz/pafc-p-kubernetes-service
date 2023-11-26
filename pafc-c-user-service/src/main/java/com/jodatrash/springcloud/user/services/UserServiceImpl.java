package com.jodatrash.springcloud.user.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jodatrash.springcloud.user.entity.Users;
import com.jodatrash.springcloud.user.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Users> findAll() {
        return (List<Users>) userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Users> byId(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public Users save(Users user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}
