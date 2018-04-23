package com.prem.priceparser.repository;


import com.prem.priceparser.domain.entity.Role;
import com.prem.priceparser.domain.enums.RoleEnum;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByRole(RoleEnum role);
}
