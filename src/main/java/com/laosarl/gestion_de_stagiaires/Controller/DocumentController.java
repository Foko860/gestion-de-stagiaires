package com.laosarl.gestion_de_stagiaires.Controller;

import com.laosarl.gestion_de_stagiaires.Service.DocumentService;
import com.laosarl.internship_management.api.DocumentsApi;
import com.laosarl.internship_management.model.CreatedDocumentIdDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class DocumentController implements DocumentsApi {
    private final DocumentService documentService;

    @Override
    public ResponseEntity<Resource> downloadPdfDocument(String id) {
        return ResponseEntity.status(HttpStatus.OK).body(documentService.downloadPdfDocument(id));
    }

    @Override
    public ResponseEntity<CreatedDocumentIdDTO> uploadPdfDocument(MultipartFile file) {
        return ResponseEntity.status(HttpStatus.CREATED).body(documentService.createDocument(file));
    }
}
