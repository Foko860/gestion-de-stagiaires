package com.laosarl.gestion_de_stagiaires.domain.supervisor;

import com.laosarl.gestion_de_stagiaires.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "supervisor")
public class Supervisor extends User {



    @Column(name = "role_in_company")
    private String companyRole;


}
