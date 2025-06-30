package com.laosarl.gestion_de_stagiaires.Service.mapper;

import com.laosarl.gestion_de_stagiaires.domain.supervisor.Supervisor;
import com.laosarl.gestion_de_stagiaires.domain.user.PhoneNumber;
import com.laosarl.internship_management.model.PhoneNumberDTO;
import com.laosarl.internship_management.model.SupervisorDTO;
import com.laosarl.internship_management.model.SupervisorRegistrationRequestDTO;
import com.laosarl.internship_management.model.UpdateSupervisorDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface SupervisorMapper {

    // ----- Mapping DTO vers Supervisor -----
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "companyRole", source = "companyRole")
       Supervisor toSupervisor(SupervisorRegistrationRequestDTO dto);

    // ----- Mapping Supervisor vers DTO -----
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "companyRole", source = "companyRole")
       SupervisorDTO toDTO(Supervisor supervisor);

    // ----- Update partiel -----
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "companyRole", source = "companyRole")
       void copyDataFromUpdateUserDTOToUser(UpdateSupervisorDTO updateSupervisorDTO, @MappingTarget Supervisor supervisor);

    // ----- Mapping PhoneNumberDTO vers PhoneNumber -----
    @Named("fromPhoneNumberDTOToPhoneNumber")
    default PhoneNumber fromPhoneNumberDTOToPhoneNumber(PhoneNumberDTO phoneNumberDTO) {
        if (phoneNumberDTO == null) return null;
        return new PhoneNumber(phoneNumberDTO.getCountryCode(), phoneNumberDTO.getNumber());
    }

    // ----- Mapping PhoneNumber vers PhoneNumberDTO -----
    @Named("fromPhoneNumberToPhoneNumberDTO")
    default PhoneNumberDTO fromPhoneNumberToPhoneNumberDTO(PhoneNumber phoneNumber) {
        if (phoneNumber == null) return null;
        PhoneNumberDTO phoneNumberDTO = new PhoneNumberDTO();
        phoneNumberDTO.setCountryCode(phoneNumber.getCountryCode());
        phoneNumberDTO.setNumber(phoneNumber.getNumber());
        return phoneNumberDTO;
    }
}
