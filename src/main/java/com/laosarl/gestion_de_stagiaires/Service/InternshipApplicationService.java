package com.laosarl.gestion_de_stagiaires.Service;

import com.laosarl.gestion_de_stagiaires.Model.InternshipApplication;
import com.laosarl.gestion_de_stagiaires.Repository.InternshipApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class InternshipApplicationService {

    private final InternshipApplicationRepository internshipApplicationRepository;

    public InternshipApplication create(InternshipApplication application) {
        application.setSubmissionDate(LocalDate.now());
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
