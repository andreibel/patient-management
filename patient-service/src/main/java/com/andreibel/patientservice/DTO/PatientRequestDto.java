package com.andreibel.patientservice.DTO;

import com.andreibel.patientservice.DTO.validators.CreatePatientValidatorGroup;
import com.andreibel.patientservice.model.Patient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link Patient}
 */
public record PatientRequestDto(
@NotNull @Size(message = "Name cannot exceed 100 characters", max = 100) @NotBlank String name,
@NotNull @Email(message = "Email should be valid") @NotBlank(message = "Email is required") String email,
@NotNull @NotBlank(message = "Address is required") String address,
@NotNull @NotBlank(message = "Date of birth is required") String dateOfBirth,
@NotBlank(groups = CreatePatientValidatorGroup.class, message = "Registered date is required") String registeredDate)
implements Serializable { }