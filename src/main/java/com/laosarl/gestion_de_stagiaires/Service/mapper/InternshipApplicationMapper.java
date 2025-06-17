package com.laosarl.gestion_de_stagiaires.Service.mapper;

import com.laosarl.gestion_de_stagiaires.domain.InternshipApplication;
import com.laosarl.internship_management.model.InternshipApplicationRequestDTO;
import com.laosarl.internship_management.model.InternshipApplicationResponseDTO;
import com.laosarl.internship_management.model.UpdateInternshipApplicationDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
//import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface InternshipApplicationMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "firstName")
    @Mapping(target = "lastName")
    @Mapping(target = "studyLevel")
    @Mapping(target = "speciality")
    @Mapping(target = "university")
    @Mapping(target = "cv", source = "cv")
    @Mapping(target = "startDate")
    @Mapping(target = "endDate")
    InternshipApplication toEntity(InternshipApplicationRequestDTO internshipApplicationRequestDTO);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id")
    @Mapping(target = "firstName")
    @Mapping(target = "lastName")
    @Mapping(target = "studyLevel")
    @Mapping(target = "speciality")
    @Mapping(target = "university")
    @Mapping(target = "cv", source = "cv")
    @Mapping(target = "startDate")
    @Mapping(target = "endDate")
    @Mapping(target = "submissionDate")
    @Mapping(target = "status")
    InternshipApplicationResponseDTO toResponseDTO(InternshipApplication internshipApplication);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDTO(UpdateInternshipApplicationDTO updateInternshipApplicationDTO, @MappingTarget InternshipApplication internshipApplication);
}
