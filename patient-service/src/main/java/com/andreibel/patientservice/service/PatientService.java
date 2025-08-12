package com.andreibel.patientservice.service;


import com.andreibel.patientservice.exception.EmailAlreadyExistsException;
import com.andreibel.patientservice.mapper.PatientMapper;
import com.andreibel.patientservice.model.Patient;
import com.andreibel.patientservice.DTO.PatientResponseDto;
import com.andreibel.patientservice.DTO.PatientRequestDto;
import com.andreibel.patientservice.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;


    /**
     * Retrieves all patients from the repository and maps them to PatientResponseDTO objects.
     *
     * @return a list of {@link PatientResponseDto} representing
     * all patients
     */
    public List<PatientResponseDto> getPatients() {
        System.out.printf("Retrieving all patients from the repository%n");
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(PatientMapper::toPatientResponseDTO).toList();

    }

    /**
     * Creates a new patient in the repository based on the provided request data.
     *
     * @param newPatient the {@link PatientRequestDto} containing patient details to be created
     * @return the created {@link PatientResponseDto} with patient information, or null if creation fails
     */
    @Transactional
    public PatientResponseDto createPatient(PatientRequestDto newPatient) {
        if(patientRepository.existsByEmail(newPatient.email())) {
            throw new EmailAlreadyExistsException("Email already exists: " + newPatient.email());
        }
        Patient patient = patientRepository.save(PatientMapper.toPatient(newPatient));
        return PatientMapper.toPatientResponseDTO(patient);
    }

}
