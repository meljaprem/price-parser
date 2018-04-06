package com.prem.priceparser.repository;

import com.prem.priceparser.domain.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

    List<User> findAllByName(String name);

    List<User> findAllBySurname(String surname);

    List<User> findByEmail(String email);
}
