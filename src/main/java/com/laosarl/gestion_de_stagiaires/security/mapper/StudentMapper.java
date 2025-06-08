package com.laosarl.gestion_de_stagiaires.security.mapper;

import com.laosarl.gestion_de_stagiaires.security.domain.student.StudentNew;
import com.laosarl.gestion_de_stagiaires.security.domain.user.PhoneNumber;
import com.laosarl.gestion_de_stagiaires.security.domain.user.UserNew;
import com.laosarl.internship_management.model.PhoneNumberDTO;
import com.laosarl.internship_management.model.StudentDTO;
import com.laosarl.internship_management.model.StudentRegistrationRequestDTO;
import com.laosarl.internship_management.model.UpdateUserDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
@Component
public interface StudentMapper {
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "firstname", source = "firstname")
    @Mapping(target = "lastname", source = "lastname")
    @Mapping(target = "gender", source = "gender.value")
    @Mapping(target = "dateOfBirth", source = "dateOfBirth")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    StudentNew toStudent(StudentRegistrationRequestDTO studentRegistrationRequestDTO);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id")
    @Mapping(target = "email")
    @Mapping(target = "firstname")
    @Mapping(target = "lastname")
    @Mapping(target = "dateOfBirth")
    @Mapping(target = "phoneNumber")
    @Mapping(target = "gender")
    @Mapping(target = "profileImageId", source = "profileImage.value")
    StudentDTO toDTO(StudentNew studentNew);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "firstname", source = "firstname")
    @Mapping(target = "lastname", source = "lastname")
    @Mapping(target = "dateOfBirth", source = "dateOfBirth")
    @Mapping(target = "gender", source = "gender")
    @Mapping(target = "phoneNumber", source = "phoneNumber", qualifiedByName = "fromPhoneNumberDTOToPhoneNumber")
    @Mapping(target = "profileImage.value", source = "profileImageId")
    void copyDataFromUpdateUserDTOToUser(UpdateUserDTO updateUserDTO, @MappingTarget UserNew user);

    @Named("fromPhoneNumberDTOToPhoneNumber")
    @Mapping(target = "countryCode", source = "countryCode")
    @Mapping(target = "number", source = "number")
    PhoneNumber fromPhoneNumberDTOToPhoneNumber(PhoneNumberDTO data);

}
