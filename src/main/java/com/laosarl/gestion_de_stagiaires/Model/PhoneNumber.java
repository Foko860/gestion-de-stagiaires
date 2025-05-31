package com.laosarl.gestion_de_stagiaires.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class PhoneNumber {

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "number")
    private String number;
}