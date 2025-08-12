package com.andreibel.patientservice.mapper;

import com.andreibel.patientservice.DTO.PatientRequestDto;
import com.andreibel.patientservice.model.Patient;
import com.andreibel.patientservice.DTO.PatientResponseDto;

import java.time.LocalDate;

public class PatientMapper {
    public static PatientResponseDto toPatientResponseDTO(Patient p) {
        return new PatientResponseDto(
                p.getId().toString(),
                p.getName(),
                p.getEmail(),
                p.getAddress(),
                p.getDateOfBirth().toString()
        );
    }
    public static Patient toPatient(PatientRequestDto dto) {
        Patient patient = new Patient();
        patient.setName(dto.name());
        patient.setEmail(dto.email());
        patient.setAddress(dto.address());
        patient.setDateOfBirth(LocalDate.parse(dto.dateOfBirth()));
        patient.setRegisteredDate(LocalDate.parse(dto.registeredDate()));
        return patient;
    }
}
