package com.laosarl.gestion_de_stagiaires.Service;

import com.laosarl.gestion_de_stagiaires.Repository.DocumentRepository;
import com.laosarl.gestion_de_stagiaires.domain.document.Document;
import com.laosarl.internship_management.model.CreatedDocumentIdDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private static final Path STORAGE_DIR = Paths.get("resources/documents");

    private final DocumentRepository documentRepository;

    @Transactional
    public CreatedDocumentIdDTO createDocument(MultipartFile file) {
        try {
            Document document = Document.builder()
                    .originalFilename(file.getOriginalFilename())
                    .fileSize(file.getSize())
                    .contentType(file.getContentType())
                    .build();

            Document savedDocument = documentRepository.save(document);

            if (!Files.exists(STORAGE_DIR)) {
                Files.createDirectories(STORAGE_DIR);
            }

            Path targetPath = STORAGE_DIR.resolve(savedDocument.getId() + ".pdf");
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
            }

            return new CreatedDocumentIdDTO().id(savedDocument.getId());

        } catch (IOException e) {
            throw new RuntimeException("Failed to store document", e);
        }
    }

    @Transactional(readOnly = true)
    public Resource downloadPdfDocument(String id) {
        try {
            Path filePath = STORAGE_DIR.resolve(id + ".pdf");

            if (!Files.exists(filePath)) {
                throw new FileNotFoundException("Document with ID " + id + " not found.");
            }

            return new UrlResource(filePath.toUri());

        } catch (MalformedURLException | FileNotFoundException e) {
            throw new RuntimeException("Failed to load document: " + e.getMessage(), e);
        }
    }
}
