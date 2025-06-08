package com.laosarl.gestion_de_stagiaires.security.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class ProfileImage {
    @Column(name = "c_profile_image")
    private UUID value;

    public ProfileImage(UUID imageIdValue) {
        this.value = imageIdValue;
    }
}
