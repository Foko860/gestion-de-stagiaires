package com.laosarl.gestion_de_stagiaires.Service;

import com.laosarl.gestion_de_stagiaires.Model.InternshipApplication;
import com.laosarl.gestion_de_stagiaires.Repository.StudentRepository;
import com.laosarl.gestion_de_stagiaires.Repository.InternshipApplicationRepository;
import com.laosarl.gestion_de_stagiaires.domain.student.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class InternshipApplicationService {

    private final InternshipApplicationRepository internshipApplicationRepository;
    private final StudentRepository studentRepository;

    public InternshipApplication create(InternshipApplication application) {
        UUID studentId = application.getStudent().getId();

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        application.setStudent(student);

        return internshipApplicationRepository.save(application);
    }
    @Transactional(readOnly = true)
    public List<InternshipApplication> getAll() {
        return internshipApplicationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<InternshipApplication> getById(Long id) {
        return internshipApplicationRepository.findById(id);
    }

    public InternshipApplication update(Long id, InternshipApplication updatedApplication) {
        InternshipApplication app = internshipApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        app.setCv(updatedApplication.getCv());
        app.setCoverLetter(updatedApplication.getCoverLetter());
        app.setStatus(updatedApplication.getStatus());

        return internshipApplicationRepository.save(app);
    }

    public void delete(Long id) {
        internshipApplicationRepository.deleteById(id);
    }
}
