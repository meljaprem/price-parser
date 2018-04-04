package com.prem.priceparser;

import com.prem.priceparser.domain.model.User;
import com.prem.priceparser.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PriceParserApplicationTests {

    @Autowired
    private UserService userService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Test
    public void createUser() {
         User user = userService.createAdmin("name",
                        "Surname",
                        "Email@email.ua",
                        "admin",
                        "admin");
         log.info("User with id {} created \n {}", user.getId(), user);
    }

}
