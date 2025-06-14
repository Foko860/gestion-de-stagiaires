package com.laosarl.gestion_de_stagiaires.Model;

import com.laosarl.gestion_de_stagiaires.Enum.InternshipApplicationStatus;
import com.laosarl.gestion_de_stagiaires.domain.student.Student;
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
    private Long id;

    private String cv;

    private String coverLetter;

    @Enumerated(EnumType.STRING)
    private InternshipApplicationStatus status;

    private LocalDate submissionDate;

    @ManyToOne(fetch = FetchType.EAGER
    )
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
}
