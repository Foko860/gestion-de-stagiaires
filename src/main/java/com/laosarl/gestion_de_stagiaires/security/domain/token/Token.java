package com.laosarl.gestion_de_stagiaires.security.domain.token;

import com.laosarl.gestion_de_stagiaires.security.common.EntityBase;
import com.laosarl.gestion_de_stagiaires.security.domain.user.UserNew;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "t_token")
public class Token extends EntityBase {
    @Column(name = "c_token")
    private String value;

    @Column(name = "c_type")
    @Enumerated(EnumType.STRING)
    private TokenType type;

    @Column(name = "c_expired")
    private boolean expired;

    @Column(name = "c_revoked")
    private boolean revoked;

    @ManyToOne
    @JoinColumn(name = "c_user_id", referencedColumnName = "c_id")
    private UserNew user;
}
