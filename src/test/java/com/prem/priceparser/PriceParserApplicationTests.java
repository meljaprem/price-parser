package com.prem.priceparser;

import com.prem.priceparser.domain.enums.RoleEnum;
import com.prem.priceparser.domain.model.Role;
import com.prem.priceparser.domain.model.User;
import com.prem.priceparser.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PriceParserApplicationTests {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Test
    public void createUser() {
        User user = new User();
        user.setName("name");
        user.setEmail("email");
        user.setPassword(encoder.encode("admin"));
        user.setUsername("admin");
        user.setSurname("surname");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(new Role(RoleEnum.ADMIN));
        user.setAuthorities(roleSet);

        userRepository.save(user);
    }

}
