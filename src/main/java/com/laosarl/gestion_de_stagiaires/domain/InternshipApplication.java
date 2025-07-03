package com.laosarl.gestion_de_stagiaires.domain;

import com.laosarl.gestion_de_stagiaires.domain.admin.Admin;
import com.laosarl.gestion_de_stagiaires.domain.document.Document;
import com.laosarl.gestion_de_stagiaires.domain.supervisor.Supervisor;
import com.laosarl.gestion_de_stagiaires.domain.user.PhoneNumber;
import com.laosarl.gestion_de_stagiaires.exceptions.BadRequestException;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
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
import java.time.LocalDateTime;
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
    @GeneratedValue
    @Column(name = "id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID internshipId;

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

    @Column(name = "document_id")
    private UUID documentId;

    @Column(name = "email")
    private String email;
    @Column(name= "start_date")
    private LocalDate startDate;

    @Column(name= "end_date")
    private LocalDate endDate;

    @Embedded
    private PhoneNumber phoneNumber;

    @Enumerated(EnumType.STRING)
    private InternshipApplicationStatus status;

    @Builder.Default
    private LocalDateTime submissionDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "supervisor_id")
    private Supervisor supervisor;


    @Column(name = "accepted_by")
    private String acceptedBy;


    @Column(name = "rejected_by")
    private String rejectedBy;


    @Column(name = "assigned_by")
    private String assignedBy;

    private String reason;

    public void markHasAccept(Admin admin) {
        this.status = InternshipApplicationStatus.ACCEPTED;
        this.acceptedBy = admin.getUsername();
    }

    public void markHasRejected(Admin admin, String reason) {
        this.status = InternshipApplicationStatus.REJECTED;
        this.reason = reason;
        this.rejectedBy = admin.getUsername();
    }

    public void assignSupervisor(Supervisor supervisor, Admin admin) {
        if (!InternshipApplicationStatus.ACCEPTED.equals(this.status)) {
            throw new BadRequestException("Cannot assign supervisor. Internship is not accepted.");
        }
        this.supervisor = supervisor;
        this.status = InternshipApplicationStatus.ASSIGN;
        this.assignedBy = admin.getUsername();
    }

    public void markHasPushed() {
        this.status = InternshipApplicationStatus.PUBLISHED;
    }
}
