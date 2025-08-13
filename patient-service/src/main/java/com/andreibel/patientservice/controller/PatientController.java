package com.andreibel.patientservice.controller;

import com.andreibel.patientservice.DTO.PatientRequestDto;
import com.andreibel.patientservice.DTO.PatientResponseDto;
import com.andreibel.patientservice.DTO.validators.CreatePatientValidatorGroup;
import com.andreibel.patientservice.service.PatientService;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing patient-related endpoints.
 * Provides endpoints to retrieve, create, and update patients.
 */
@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {
    /**
     * Service for handling patient business logic.
     */
    private final PatientService patientService;

    /**
     * Retrieves a list of all patients.
     *
     * @return {@link ResponseEntity} containing a list of {@link PatientResponseDto} objects
     */
    @GetMapping
    public ResponseEntity<List<PatientResponseDto>> getPatients() {
        return ResponseEntity.ok().body(patientService.getPatients());
    }

    /**
     * Creates a new patient with the provided details.
     *
     * @param newPatient the {@link PatientRequestDto} containing patient information to create
     * @return {@link ResponseEntity} containing the created {@link PatientResponseDto}
     */
    @PostMapping
    public ResponseEntity<PatientResponseDto> createPatient(@Validated({Default.class, CreatePatientValidatorGroup.class})
                                                            @RequestBody PatientRequestDto newPatient) {
        PatientResponseDto patientResponseDto = patientService.createPatient(newPatient);
        return ResponseEntity.ok().body(patientResponseDto);
    }

    /**
     * Updates an existing patient with the given ID using the provided details.
     *
     * @param patientId         the ID of the patient to update, as a UUID
     * @param patientRequestDto the {@link PatientRequestDto} containing updated patient information
     * @return {@link ResponseEntity} containing the updated {@link PatientResponseDto}
     */
    @PutMapping("/{patientId}")
    public ResponseEntity<PatientResponseDto> updatePatient(@PathVariable("patientId") UUID patientId,
                                                            @Validated({Default.class})
                                                            @RequestBody PatientRequestDto patientRequestDto) {
        PatientResponseDto patientResponseDto = patientService.updatePatient(patientId, patientRequestDto);
        return ResponseEntity.ok().body(patientResponseDto);
    }

    /**
     * Deletes a patient with the specified ID.
     *
     * @param patientId the {@link UUID} of the patient to delete
     * @return {@link ResponseEntity} with HTTP 204 No Content status if deletion is successful
     * (no content is returned in the response body)
     */
    @DeleteMapping("/{patientId}")
    public ResponseEntity<Void> deletePatient(@PathVariable("patientId") UUID patientId) {
        patientService.deletePatient(patientId);
        return ResponseEntity.noContent().build();
    }

}