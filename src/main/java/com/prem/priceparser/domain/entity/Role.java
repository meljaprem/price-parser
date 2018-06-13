package com.prem.priceparser.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prem.priceparser.domain.enums.RoleEnum;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;

@Entity
@EqualsAndHashCode(exclude = "users")
@ToString(exclude = "users")
@NoArgsConstructor
@Table(name = "roles")
public class Role implements GrantedAuthority {

    public Role(RoleEnum role) {
        this.role = role;
    }
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,
            length = 5,
            unique = true

    )
    private RoleEnum role = RoleEnum.USER;
    @JsonIgnore
    @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
    private Collection<User> users;


    @Override
    public String getAuthority() {
        return role.name();
    }
}
