package com.prem.priceparser.services;


import com.prem.priceparser.domain.enums.RoleEnum;
import com.prem.priceparser.domain.entity.Role;
import com.prem.priceparser.domain.entity.User;
import com.prem.priceparser.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Getter
@Setter
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        passwordEncoder = new BCryptPasswordEncoder();
    }

    public User createUser(String name, String surname, String email, String username, String pass) {
        User user = getFilledUser(name, surname, email, username, pass);
        return createUser(user);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User createAdmin(User adminUser) {
        adminUser.getAuthorities().add(new Role(RoleEnum.ADMIN));
        return userRepository.save(adminUser);
    }

    public User createAdmin(String name, String surname, String email, String username, String pass) {
        User adminUser = getFilledUser(name, surname, email, username, pass);
        return createAdmin(adminUser);
    }

    private User getFilledUser(String name, String surname, String email, String username, String pass) {
        User user = User.builder()
                .name(name)
                .surname(surname)
                .email(email.toLowerCase())
                .username(username.toLowerCase())
                .password(passwordEncoder.encode(pass))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .enabled(true)
                .credentialsNonExpired(true)
                .build();
        user.getAuthorities().add(new Role(RoleEnum.USER));
        return user;
    }

}
