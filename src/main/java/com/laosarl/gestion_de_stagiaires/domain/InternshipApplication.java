package com.laosarl.gestion_de_stagiaires.domain;

import com.laosarl.gestion_de_stagiaires.domain.student.Student;
import com.laosarl.gestion_de_stagiaires.domain.user.PhoneNumber;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "internship_applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternshipApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "first_name")
    private String  firstName;

    @Column(name = "last_name")
    private String lastName;
    @Column(name = "study_level")
    private String studyLevel;

    @Column(name = "speciality")
    private String speciality;

    @Column(name = "university")
    private String university;

    @Column(name = "curriculum_vitae")
    private String cv;

    @Column(name= "start_date")
    private LocalDate startDate;

    @Column(name= "end_date")
    private LocalDate endDate;

    @Embedded
    private PhoneNumber phoneNumber;

    @Enumerated(EnumType.STRING)
    private InternshipApplicationStatus status;

    private LocalDate submissionDate;

}
