package com.prem.priceparser.services;

import com.prem.priceparser.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Data
@Slf4j
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.trace("Trying to login with username {}", username);
        UserDetails user = userRepository.findByUsername(username.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("LogIn failed, user with username '" + username + "' not found");
        } else {
            log.debug("User found: {}", user);
        }
        return user;
    }
}
