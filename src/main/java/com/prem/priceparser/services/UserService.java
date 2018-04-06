package com.prem.priceparser.services;


import com.prem.priceparser.domain.enums.RoleEnum;
import com.prem.priceparser.domain.entity.Role;
import com.prem.priceparser.domain.entity.User;
import com.prem.priceparser.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public User createUser(User user) {
        return createUser(user, RoleEnum.USER);
    }

    public User createUser(String name, String surname, String email, String username, String pass) {
        User user = new User(name, surname, email, username, pass);
        return createUser(user, RoleEnum.USER);
    }

    public User createUser(User user, RoleEnum ... roles) {
        log.debug("Creating user: {} with roles: {}", user, roles);
        normalizeUserFields(user);
        setAccountSetting(user, roles);
        userRepository.save(user);
        log.debug("User created: {}", user);
        return user;
    }

    public List<User> getAllUsers(){
        return (List<User>) userRepository.findAll();
    }

    public Optional<User> getUserById(Long userId){
        return userRepository.findById(userId);
    }

    private void setAccountSetting(User user, RoleEnum... roles) {
        for (RoleEnum role : roles) {
            user.getAuthorities().add(new Role(role));
        }
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
    }

    private void normalizeUserFields(User user) {
                user.setEmail(user.getEmail().toLowerCase());
                user.setUsername(user.getUsername().toLowerCase());
                user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
