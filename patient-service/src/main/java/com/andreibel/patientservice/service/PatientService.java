package com.andreibel.patientservice.service;

import com.andreibel.patientservice.DTO.PatientRequestDto;
import com.andreibel.patientservice.DTO.PatientResponseDto;
import com.andreibel.patientservice.exception.EmailAlreadyExistsException;
import com.andreibel.patientservice.exception.PatientNotFoundException;
import com.andreibel.patientservice.mapper.PatientMapper;
import com.andreibel.patientservice.model.Patient;
import com.andreibel.patientservice.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Service class for managing patient-related operations.
 * Provides methods to retrieve, create, and update patients,
 * as well as to check for email uniqueness.
 */
@Service
@RequiredArgsConstructor
public class PatientService {
    /**
     * Repository for accessing patient data.
     */
    private final PatientRepository patientRepository;

    /**
     * Retrieves all patients from the repository and maps them to PatientResponseDTO objects.
     *
     * @return a list of {@link PatientResponseDto} representing all patients;
     * returns an empty list if no patients are found.
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
     * @return the created {@link PatientResponseDto} with patient information
     * @throws EmailAlreadyExistsException if the provided email already exists in the repository
     */
    @Transactional
    public PatientResponseDto createPatient(PatientRequestDto newPatient) {
        if (patientRepository.existsByEmail(newPatient.email())) {
            throw new EmailAlreadyExistsException("Email already exists: " + newPatient.email());
        }
        Patient patient = patientRepository.save(PatientMapper.toPatient(newPatient));
        return PatientMapper.toPatientResponseDTO(patient);
    }

    /**
     * Updates an existing patient in the repository with the provided request data.
     *
     * @param patientId         the {@link UUID} of the patient to update
     * @param patientRequestDto the {@link PatientRequestDto} containing updated patient details
     * @return the updated {@link PatientResponseDto} with patient information
     * @throws PatientNotFoundException    if no patient is found with the given ID
     * @throws EmailAlreadyExistsException if the provided email already exists in the repository by other patients
     */
    @Transactional
    public PatientResponseDto updatePatient(UUID patientId, PatientRequestDto patientRequestDto) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(
                () -> new PatientNotFoundException("Patient not found with ID: " + patientId));
        if (patientRepository.existsByEmailAndIdNot(patientRequestDto.email(), patientId)) {
            throw new EmailAlreadyExistsException("Email already exists: " + patientRequestDto.email());
        }
        patient.setName(patientRequestDto.name());
        patient.setEmail(patientRequestDto.email());
        patient.setAddress(patientRequestDto.address());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDto.dateOfBirth()));
        patient = patientRepository.save(patient);
        return PatientMapper.toPatientResponseDTO(patient);
    }
}