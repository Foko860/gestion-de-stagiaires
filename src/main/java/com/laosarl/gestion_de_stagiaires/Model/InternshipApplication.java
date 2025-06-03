package com.laosarl.gestion_de_stagiaires.Model;

import com.laosarl.gestion_de_stagiaires.Enum.InternshipApplicationStatus;
import jakarta.persistence.*;
import lombok.*;
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

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
}
