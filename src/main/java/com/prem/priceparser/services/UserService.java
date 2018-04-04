package com.prem.priceparser.services;


import com.prem.priceparser.domain.enums.RoleEnum;
import com.prem.priceparser.domain.model.Role;
import com.prem.priceparser.domain.model.User;
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

    public User createUser(String name, String surname, String email, String username, String pass){
        User user = User.builder()
                .name(name)
                .surname(surname)
                .email(email)
                .username(username)
                .password(passwordEncoder.encode(pass))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .enabled(true)
                .credentialsNonExpired(true)
                .build();
        return createUser(user);
    }

    public User createUser(User user){
        return userRepository.save(user);
    }

    public User createAdmin(User user){
        user.getAuthorities().add(new Role(RoleEnum.ADMIN));
        return userRepository.save(user);
    }

    public User createAdmin(String name, String surname, String email, String username, String pass){
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
        return createAdmin(user);
    }
}
