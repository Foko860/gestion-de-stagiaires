package com.laosarl.gestion_de_stagiaires.security.domain.student;

import com.laosarl.gestion_de_stagiaires.security.domain.user.UserNew;
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
@Table(name = "t_student")
public class StudentNew extends UserNew {

    @Column(name = "study_level")
    private String studyLevel;

    @Column(name = "speciality")
    private String speciality;

    @Column(name = "university")
    private String university;
}
