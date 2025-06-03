package com.laosarl.gestion_de_stagiaires.Controller;

import com.laosarl.gestion_de_stagiaires.Model.InternshipApplication;
import com.laosarl.gestion_de_stagiaires.Service.InternshipApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/applications")
public class InternshipApplicationController {

    private final InternshipApplicationService internshipApplicationService;

    @PostMapping
    public ResponseEntity<InternshipApplication> create(@RequestBody InternshipApplication application) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(internshipApplicationService.create(application));
    }

    @GetMapping
    public ResponseEntity<List<InternshipApplication>> getAll() {
        return ResponseEntity.ok(internshipApplicationService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InternshipApplication> getById(@PathVariable Long id) {
        return internshipApplicationService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<InternshipApplication> update(@PathVariable Long id, @RequestBody InternshipApplication application) {
        return ResponseEntity.ok(internshipApplicationService.update(id, application));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        internshipApplicationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
