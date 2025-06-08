package com.laosarl.gestion_de_stagiaires.security.domain.user;

import com.laosarl.gestion_de_stagiaires.security.common.EntityBase;
import com.laosarl.gestion_de_stagiaires.security.domain.token.Token;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class UserNew extends EntityBase implements UserDetails {
    @Column(name = "c_email")
    private String email;

    @Column(name = "c_password")
    private String password;

    @Column(name = "c_firstname")
    private String firstname;

    @Column(name = "c_lastname")
    private String lastname;

    @Column(name = "c_date_of_bird")
    private LocalDate dateOfBirth;

    @Embedded
    private PhoneNumber phoneNumber;

    @Column(name = "c_gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "c_role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Token> tokenList;

    @Embedded
    private ProfileImage profileImage;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
