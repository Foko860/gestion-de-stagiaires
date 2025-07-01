package com.laosarl.gestion_de_stagiaires.Service.mapper;

import com.laosarl.gestion_de_stagiaires.Repository.DocumentRepository;
import com.laosarl.gestion_de_stagiaires.domain.InternshipApplication;
import com.laosarl.gestion_de_stagiaires.domain.document.Document;
import com.laosarl.gestion_de_stagiaires.domain.user.PhoneNumber;
import com.laosarl.internship_management.model.*;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface InternshipApplicationMapper {

    // ===== Mapping DTO -> Entity =====
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "studyLevel", source = "studyLevel")
    @Mapping(target = "speciality", source = "speciality")
    @Mapping(target = "university", source = "university")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "documentId", source = "documentId", qualifiedByName = "mapUuidToDocument")
    @Mapping(target = "phoneNumber", source = "phoneNumber", qualifiedByName = "mapPhoneNumberDTOToPhoneNumber")
    InternshipApplication toEntity(
            InternshipApplicationRequestDTO internshipApplicationRequestDTO,
            @Context DocumentRepository documentRepository
    );

    // ===== Mapping Entity -> DTO =====
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "internshipId", source = "internshipId")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "studyLevel", source = "studyLevel")
    @Mapping(target = "speciality", source = "speciality")
    @Mapping(target = "university", source = "university")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "submissionDate", source = "submissionDate")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "documentId", source = "documentId", qualifiedByName = "mapDocumentToUuid")
    @Mapping(target = "phoneNumber", source = "phoneNumber", qualifiedByName = "mapPhoneNumberToPhoneNumberDTO")
    InternshipApplicationResponseDTO toResponseDTO(InternshipApplication entity);

    // ===== Partial Update DTO -> Entity =====
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "phoneNumber", source = "phoneNumber", qualifiedByName = "mapPhoneNumberDTOToPhoneNumber")
    @Mapping(target = "documentId", source = "documentId", qualifiedByName = "mapUuidToDocument")
    void updateFromDTO(
            UpdateInternshipApplicationDTO dto,
            @MappingTarget InternshipApplication entity,
            @Context DocumentRepository documentRepository
    );

    // ===== Mapping for PhoneNumber =====
    @Named("mapPhoneNumberDTOToPhoneNumber")
    default PhoneNumber mapPhoneNumberDTOToPhoneNumber(PhoneNumberDTO dto) {
        if (dto == null) return null;
        return new PhoneNumber(dto.getCountryCode(), dto.getNumber());
    }

    @Named("mapPhoneNumberToPhoneNumberDTO")
    default PhoneNumberDTO mapPhoneNumberToPhoneNumberDTO(PhoneNumber entity) {
        if (entity == null) return null;
        PhoneNumberDTO dto = new PhoneNumberDTO();
        dto.setCountryCode(entity.getCountryCode());
        dto.setNumber(entity.getNumber());
        return dto;
    }

    // ===== Mapping UUID <-> Document =====
    @Named("mapUuidToDocument")
    default Document mapUuidToDocument(UUID id, @Context DocumentRepository documentRepository) {
        if (id == null) return null;
        return documentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Document not found with id: " + id));
    }

    @Named("mapDocumentToUuid")
    default UUID mapDocumentToUuid(Document document) {
        return document != null ? document.getId() : null;
    }
}
