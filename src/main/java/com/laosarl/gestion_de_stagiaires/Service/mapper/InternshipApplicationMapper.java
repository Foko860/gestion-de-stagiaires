package com.laosarl.gestion_de_stagiaires.Service.mapper;

import com.laosarl.gestion_de_stagiaires.domain.InternshipApplication;
import com.laosarl.gestion_de_stagiaires.domain.document.Document;
import com.laosarl.internship_management.model.InternshipApplicationDTO;
import com.laosarl.internship_management.model.InternshipApplicationRequestDTO;
import com.laosarl.internship_management.model.UpdateInternshipApplicationDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

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
    @Mapping(target = "documentId", source = "documentId")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    InternshipApplication toEntity(InternshipApplicationRequestDTO internshipApplicationRequestDTO);

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
    @Mapping(target = "documentId", source = "documentId")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    InternshipApplicationDTO toResponseDTO(InternshipApplication entity);

    // ===== Partial Update DTO -> Entity =====
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "university")
    void updateFromDTO(UpdateInternshipApplicationDTO dto, @MappingTarget InternshipApplication entity);

    @Named("mapDocumentToUuid")
    default UUID mapDocumentToUuid(Document document) {
        return document != null ? document.getId() : null;
    }
}
