package com.prem.priceparser.listeners;

import com.prem.priceparser.domain.entity.User;
import com.prem.priceparser.domain.enums.RoleEnum;
import com.prem.priceparser.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class ContextRefreshListener implements ApplicationListener<ContextRefreshedEvent> {

    private final UserService userService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.debug("Checking if user with username <admin>  exists");
        User admin = userService.getUserByUsername("admin");
        if (admin == null) {
            log.debug("User with username <admin> doesn't exist. Creating...");
            createUser();
        } else {
            log.debug("User with username <admin> exists!");
        }
    }


    private void createUser() {
        User user = new User("name",
                "Surname",
                "Email@email.ua",
                "admin",
                "admin");
        userService.createUser(user, RoleEnum.USER, RoleEnum.ADMIN);
    }
}
