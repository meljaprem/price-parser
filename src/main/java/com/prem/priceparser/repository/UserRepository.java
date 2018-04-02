package com.prem.priceparser.repository;

import com.prem.priceparser.domain.model.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Long> {

}
