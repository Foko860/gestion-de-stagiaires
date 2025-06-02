package com.laosarl.gestion_de_stagiaires.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


import java.time.LocalDate;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@PrimaryKeyJoinColumn(name = "user_id")
public class Student extends User {

    @Column(name = "study_level")
    private String studyLevel;

    @Column(name = "speciality")
    private String speciality;

    @Column(name = "university")
    private String university;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @PrePersist
    public void prePersist() {
        if (this.registrationDate == null) {
            this.registrationDate = LocalDate.now();
        }
    }

}
