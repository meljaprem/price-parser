package com.prem.priceparser.domain.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@ToString
@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String surname;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private boolean accountNonExpired;
    @Column(nullable = false)
    private boolean accountNonLocked;
    @Column(nullable = false)
    private boolean credentialsNonExpired;
    @Column(nullable = false)
    private boolean enabled;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "users_roles",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "role") }
    )
    private Collection<Role> authorities;
}
