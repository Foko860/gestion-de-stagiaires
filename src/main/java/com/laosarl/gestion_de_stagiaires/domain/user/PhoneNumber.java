package com.laosarl.gestion_de_stagiaires.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class PhoneNumber {
    @Column(name = "c_country_code")
    private String countryCode;
    @Column(name = "c_number")
    private String number;

    public PhoneNumber(String countryCode, String number) {
        this.countryCode = countryCode;
        this.number = number;
    }
}
