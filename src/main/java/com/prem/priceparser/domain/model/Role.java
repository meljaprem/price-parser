package com.prem.priceparser.domain.model;

import com.prem.priceparser.domain.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,
            length = 5
            )
    private RoleEnum role = RoleEnum.USER;

    @ManyToMany(mappedBy = "authorities")
    private Collection<User> users;


    @Override
    public String getAuthority() {
        return role.name();
    }
}
