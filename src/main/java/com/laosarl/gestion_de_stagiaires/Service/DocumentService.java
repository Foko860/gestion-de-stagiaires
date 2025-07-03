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
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private static final Path STORAGE_DIR = Paths.get("resources/documents");

    private final DocumentRepository documentRepository;

    public CreatedDocumentIdDTO createDocument(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Fichier vide");
        }

        try {
            String originalFilename = Optional.ofNullable(file.getOriginalFilename())
                    .filter(name -> !name.trim().isEmpty())
                    .orElse("document_" + System.currentTimeMillis());

            Document document = Document.builder()
                    .originalFilename(originalFilename)
                    .fileSize(file.getSize())
                    .contentType(file.getContentType())
                    .build();

            Document savedDocument = documentRepository.save(document);

            Path storageDir = Paths.get("storage", "documents");

            if (!Files.exists(storageDir)) {
                Files.createDirectories(storageDir);
            }

            String fileExtension = getFileExtension(originalFilename, file.getContentType());

            Path targetPath = storageDir.resolve(savedDocument.getId().toString() + fileExtension);

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
            }

            return new CreatedDocumentIdDTO().id(savedDocument.getId());

        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du stockage du fichier : " + e.getMessage(), e);
        }
    }


    private String getFileExtension(String originalFilename, String contentType) {
        if (originalFilename != null && originalFilename.contains(".")) {
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            if (extension.length() > 1) {
                return extension;
            }
        }
        if (contentType != null) {
            return switch (contentType.toLowerCase()) {
                case "application/pdf" -> ".pdf";
                case "text/plain" -> ".txt";
                case "application/msword" -> ".doc";
                case "application/vnd.openxmlformats-officedocument.wordprocessingml.document" -> ".docx";
                default -> ".bin";
            };
        }
        return ".bin";
    }

    @Transactional(readOnly = true)
    public Resource downloadDocument(String id) {
        try {
            Path storageDir = Paths.get("storage", "documents");

            if (!Files.exists(storageDir)) {
                throw new FileNotFoundException("Répertoire de stockage inexistant.");
            }

            try (Stream<Path> files = Files.list(storageDir)) {
                Optional<Path> matchingFile = files
                        .filter(path -> path.getFileName().toString().startsWith(id + "."))
                        .findFirst();

                if (matchingFile.isEmpty()) {
                    throw new FileNotFoundException("Aucun document trouvé pour l'ID : " + id);
                }

                return new UrlResource(matchingFile.get().toUri());
            }

        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement du document : " + e.getMessage(), e);
        }
    }
}
