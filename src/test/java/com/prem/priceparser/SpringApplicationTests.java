package com.prem.priceparser;

import com.prem.priceparser.domain.entity.User;
import com.prem.priceparser.services.pricecheckers.ComfyPriceChecker;
import com.prem.priceparser.services.pricecheckers.RozetkaPriceChecker;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class SpringApplicationTests {

    @Autowired
    private RozetkaPriceChecker rozetkaPriceChecker;


    @Autowired
    private ComfyPriceChecker comfyPriceChecker;

    @Test
    @Ignore
    public void createUser() {
         User user = new User("name",
                        "Surname",
                        "Email@email.ua",
                        "admin2",
                        "admin");
//         userService.createUser(user,RoleEnum.USER, RoleEnum.ADMIN);
         log.info("User with id {} created \n {}", user.getId(), user);
    }

    @Test
    public void rozetkaPriceCheckerTest() throws IOException {
    rozetkaPriceChecker.getPrice("5563017");
    }

    @Test
    public void comfyPriceCheckerTest() throws IOException {
    comfyPriceChecker.getPrice("smartfon-lg-g6-h870-dual-sim-platinum-acsessuary");
    }



}
