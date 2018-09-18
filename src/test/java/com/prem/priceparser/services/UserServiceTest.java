package com.prem.priceparser.services;

import com.prem.priceparser.domain.entity.Role;
import com.prem.priceparser.domain.entity.User;
import com.prem.priceparser.domain.enums.RoleEnum;
import com.prem.priceparser.repository.RoleRepository;
import com.prem.priceparser.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private BCryptPasswordEncoder encoder;

    private User user;

    @InjectMocks
    private UserService userService;

    @Before
    public void beforeTestLogic() {
        user = new User();
        user.setName("someName");
        user.setEmail("SomeEmail@email.email");
        user.setPassword("SomePassword");
        user.setSurname("SomeSurname");
        user.setUsername("someUsername");
    }


    @Test
    public void createUser() {
        Long idToSet = 123L;
        String encodedPass = "encodedPassword";

        setMockitoConditions(idToSet, encodedPass);

        User user = userService.createUser(this.user);
        assertEquals("The ids aren't equals", idToSet, user.getId());
        assertEquals("The passwords aren't equals", encodedPass, user.getPassword());
        verify(encoder, only()).encode(anyString());
        verify(roleRepository, only()).findByRole(any());
        verify(userRepository, only()).save(any());
    }

    @Test
    public void createUserWithParams() {
        Long idToSet = 123L;
        String encodedPass = "encodedPassword";

        setMockitoConditions(idToSet, encodedPass);

        User user = userService.createUser("someName",
                "SomeSurname",
                "SomeEmail@email.email",
                "someUsername",
                "SomePassword");

        assertEquals("The ids aren't equals", new Long(123L), user.getId());
        assertEquals("The passwords aren't equals", "encodedPassword", user.getPassword());
        verify(encoder, only()).encode(anyString());
        verify(roleRepository, only()).findByRole(any());
        verify(userRepository, only()).save(any());
    }

    private void setMockitoConditions(Long idToSet, String encodedPass) {
        when(encoder.encode(anyString())).thenReturn(encodedPass);

        when(roleRepository.findByRole(any(RoleEnum.class))).thenAnswer(invocation -> {
            RoleEnum roleEnum = invocation.getArgument(0);
            Role role = new Role(roleEnum);
            role.setId(1L);
            return role;
        });

        when(userRepository.save(any(User.class))).then((Answer<User>) invocation -> {
            User user = invocation.getArgument(0);
            user.setId(idToSet);
            return user;
        });
    }
}