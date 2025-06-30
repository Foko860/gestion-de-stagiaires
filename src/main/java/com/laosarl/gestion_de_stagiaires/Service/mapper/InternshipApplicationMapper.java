package com.laosarl.gestion_de_stagiaires.Service.mapper;

import com.laosarl.gestion_de_stagiaires.domain.InternshipApplication;
import com.laosarl.gestion_de_stagiaires.domain.user.PhoneNumber;
import com.laosarl.internship_management.model.InternshipApplicationRequestDTO;
import com.laosarl.internship_management.model.InternshipApplicationResponseDTO;
import com.laosarl.internship_management.model.UpdateInternshipApplicationDTO;
import com.laosarl.internship_management.model.PhoneNumberDTO;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface InternshipApplicationMapper {

    // ----- Mapping DTO -> Entity -----
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "firstName")
    @Mapping(target = "lastName")
    @Mapping(target = "studyLevel")
    @Mapping(target = "speciality")
    @Mapping(target = "university")
    @Mapping(target = "cv", source = "cv")
    @Mapping(target = "email")
    @Mapping(target = "startDate")
    @Mapping(target = "endDate")
    @Mapping(target = "phoneNumber", source = "phoneNumber", qualifiedByName = "mapPhoneNumberDTOToPhoneNumber")
    InternshipApplication toEntity(InternshipApplicationRequestDTO internshipApplicationRequestDTO);

    // ----- Mapping Entity -> DTO -----
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "internshipId")
    @Mapping(target = "firstName")
    @Mapping(target = "lastName")
    @Mapping(target = "studyLevel")
    @Mapping(target = "speciality")
    @Mapping(target = "university")
    @Mapping(target = "email")
    @Mapping(target = "cv", source = "cv")
    @Mapping(target = "startDate")
    @Mapping(target = "endDate")
    @Mapping(target = "submissionDate")
    @Mapping(target = "status")
    @Mapping(target = "phoneNumber", source = "phoneNumber", qualifiedByName = "mapPhoneNumberToPhoneNumberDTO")
    InternshipApplicationResponseDTO toResponseDTO(InternshipApplication entity);

    // ----- Partial Update DTO -> Entity -----
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "phoneNumber", source = "phoneNumber", qualifiedByName = "mapPhoneNumberDTOToPhoneNumber")
    void updateFromDTO(UpdateInternshipApplicationDTO updateInternshipApplicationDTO, @MappingTarget InternshipApplication entity);

    // ----- Mapping embedded PhoneNumber -----
    @Named("mapPhoneNumberDTOToPhoneNumber")
    default PhoneNumber mapPhoneNumberDTOToPhoneNumber(PhoneNumberDTO phoneNumberDTO) {
        if (phoneNumberDTO == null) return null;
        return new PhoneNumber(phoneNumberDTO.getCountryCode(), phoneNumberDTO.getNumber());
    }

    @Named("mapPhoneNumberToPhoneNumberDTO")
    default PhoneNumberDTO mapPhoneNumberToPhoneNumberDTO(PhoneNumber entity) {
        if (entity == null) return null;
        PhoneNumberDTO phoneNumberDTO = new PhoneNumberDTO();
        phoneNumberDTO.setCountryCode(entity.getCountryCode());
        phoneNumberDTO.setNumber(entity.getNumber());
        return phoneNumberDTO;
    }
}
