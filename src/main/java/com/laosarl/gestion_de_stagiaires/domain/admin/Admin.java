package com.laosarl.gestion_de_stagiaires.domain.admin;

import com.laosarl.gestion_de_stagiaires.domain.common.EntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "admins")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin extends EntityBase implements UserDetails {

    private String name;
    private String email;
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_ADMIN");
    }

    @Override
    public String getUsername() {
        return email; // Email comme identifiant
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // À adapter si tu veux gérer l'expiration
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // À adapter si tu veux gérer le verrouillage
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // À adapter si tu veux gérer l'expiration des credentials
    }

    @Override
    public boolean isEnabled() {
        return true; // À adapter si tu veux activer/désactiver un compte
    }
}
