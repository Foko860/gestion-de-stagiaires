package com.laosarl.gestion_de_stagiaires.Service.mapper;

import com.laosarl.gestion_de_stagiaires.domain.student.Student;
import com.laosarl.gestion_de_stagiaires.domain.user.PhoneNumber;
import com.laosarl.gestion_de_stagiaires.domain.user.User;
import com.laosarl.internship_management.model.PhoneNumberDTO;
import com.laosarl.internship_management.model.StudentDTO;
import com.laosarl.internship_management.model.StudentRegistrationRequestDTO;
import com.laosarl.internship_management.model.UpdateStudentDTO;
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
    @Mapping(target = "password")
    @Mapping(target = "firstname")
    @Mapping(target = "lastname")
    @Mapping(target = "gender", source = "gender.value")
    @Mapping(target = "dateOfBirth")
    @Mapping(target = "phoneNumber")
    Student toStudent(StudentRegistrationRequestDTO studentRegistrationRequestDTO);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "firstName", source = "firstname")
    @Mapping(target = "lastName", source = "lastname")
    @Mapping(target = "speciality")
    @Mapping(target = "studyLevel")
    @Mapping(target = "university")
    @Mapping(target = "phoneNumber")
    @Mapping(target = "gender")
    StudentDTO toDTO(Student student);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "firstname", source = "firstname")
    @Mapping(target = "lastname", source = "lastname")
    @Mapping(target = "dateOfBirth", source = "dateOfBirth")
    @Mapping(target = "gender", source = "gender")
    @Mapping(target = "phoneNumber", source = "phoneNumber", qualifiedByName = "fromPhoneNumberDTOToPhoneNumber")
    void copyDataFromUpdateUserDTOToUser(UpdateStudentDTO updateStudentDTO, @MappingTarget User user);

    @Named("fromPhoneNumberDTOToPhoneNumber")
    @Mapping(target = "countryCode", source = "countryCode")
    @Mapping(target = "number", source = "number")
    PhoneNumber fromPhoneNumberDTOToPhoneNumber(PhoneNumberDTO data);

}
