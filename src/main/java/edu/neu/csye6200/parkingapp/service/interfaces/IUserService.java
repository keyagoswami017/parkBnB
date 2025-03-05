package edu.neu.csye6200.parkingapp.service.interfaces;

import org.springframework.validation.BindingResult;

import java.util.Optional;

public interface IUserService<T> {
    Optional<T> findUserByEmailAndPassword(String email, String password);
    Optional<T> getUserById(Long id);
    T saveUser(T user, BindingResult bindingResult);
}
